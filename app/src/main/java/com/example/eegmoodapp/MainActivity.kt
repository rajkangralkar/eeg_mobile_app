package com.example.eegmoodapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.Interpreter
import java.io.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class MainActivity : AppCompatActivity() {
    private var interpreter: Interpreter? = null
    private val featureSize = 2548
    private val labelDict = arrayOf("STRESSED", "CALM", "GOOD-MOOD")

    // Modern file picker launcher
    private lateinit var csvPickerLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load model
        interpreter = Interpreter(loadModelFile())

        // Register activity result launcher for CSV picking
        csvPickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data?.data
                uri?.let {
                    // Optional: Persist permission (good for Android 10 or lower)
                    try {
                        contentResolver.takePersistableUriPermission(
                            it,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    val resultView = findViewById<TextView>(R.id.resultText)
                    val features = parseCSV(it)
                    if (features != null && features.size == featureSize) {
                        val mood = predictMood(features)
                        val song = getSongLink(mood)
                        resultView.text = "Mood: $mood\n\nSuggested Song: ${song.first}\n${song.second}"
                    } else {
                        resultView.text = "Invalid or incomplete EEG data in the selected CSV file."
                    }
                }
            }
        }

        // Launch local-only file picker on button click
        findViewById<Button>(R.id.pickFileButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*" // Accept any file type
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("text/csv", "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            csvPickerLauncher.launch(intent)
        }
    }

    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor = assets.openFd("eeg_gru_model_fixed.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.startOffset, fileDescriptor.declaredLength)
    }

    override fun onDestroy() {
        interpreter?.close()
        super.onDestroy()
    }

    private fun parseCSV(uri: Uri): FloatArray? {
        return try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                val reader = BufferedReader(InputStreamReader(inputStream))
                var line: String? = reader.readLine()

                // Skip non-numeric headers
                if (line != null && !line.matches(Regex("[0-9.,\\s-]+"))) {
                    line = reader.readLine()
                }

                if (line == null) return null

                val values = line.split(",")
                    .mapNotNull { it.trim().toFloatOrNull() }
                    .take(featureSize)

                return if (values.size == featureSize) values.toFloatArray() else null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun predictMood(features: FloatArray): String {
        val inputBuffer = ByteBuffer.allocateDirect(4 * featureSize).order(ByteOrder.nativeOrder())
        for (value in features) inputBuffer.putFloat(value)
        inputBuffer.rewind()

        val output = Array(1) { FloatArray(labelDict.size) }
        interpreter?.run(inputBuffer, output)

        val predictedIndex = output[0].indices.maxByOrNull { output[0][it] } ?: 0
        return labelDict[predictedIndex]
    }

    private fun getSongLink(mood: String): Pair<String, String> {
        return when (mood) {
            "STRESSED" -> "Let It Go - Idina Menzel" to "https://open.spotify.com/track/6f807x0ima9a1j3VPbc7VN"
            "CALM" -> "Weightless - Marconi Union" to "https://open.spotify.com/track/2V65y3PX4DkRhy1djlxd9p"
            "GOOD-MOOD" -> "Happy - Pharrell Williams" to "https://open.spotify.com/track/60nZcImufyMA1MKQY3dcCH"
            else -> "Unknown" to "#"
        }
    }
}
