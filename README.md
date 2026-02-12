# 🧠 EEG Mood Prediction Android App 🎧  
### AI-Powered Brainwave Emotion Detection & Smart Music Recommendation

An advanced **Android-based Brain-Computer Interface (BCI) application** that analyzes **EEG (Electroencephalogram) signals** using a **Deep Learning GRU model** to detect emotional states and intelligently recommend music based on the user's mental condition.

This project integrates **Neuroscience + Deep Learning + Mobile Development** into a real-world mental wellness solution.

---

# 🌍 Project Vision

Mental health monitoring should be:

- 📱 Portable  
- ⚡ Real-time  
- 🧠 Intelligent  
- 🎵 Therapeutic  

This application transforms raw EEG signals into meaningful emotional insights and provides mood-adaptive music therapy.

---

# 🧠 Predicted Mood Classes

- 🔴 **STRESSED**
- 🟢 **CALM**
- 🔵 **GOOD MOOD**

---

# 🏗️ System Architecture

EEG Device → CSV Signal Data → GRU Deep Learning Model → Mood Prediction → Music Recommendation


### Pipeline Overview:

1. EEG signals collected from device
2. Data exported as CSV
3. Preprocessing & normalization
4. GRU model inference (TensorFlow Lite)
5. Mood classification
6. Music recommendation triggered

---

# 🚀 Features

- 📂 Upload EEG signal data (CSV format)
- 🧠 GRU-based deep learning mood classification
- 📊 EEG signal visualization (MPAndroidChart)
- 🎵 Mood-based music recommendation engine
- ⚡ On-device AI inference (No cloud dependency)
- 🔒 Offline & privacy-focused

---

# 🛠️ Tech Stack

### 📱 Mobile
- Android (Java / Kotlin)
- Android Studio

### 🧠 AI / ML
- GRU (Gated Recurrent Unit)
- TensorFlow / Keras
- TensorFlow Lite (for mobile deployment)

### 📊 Visualization
- MPAndroidChart

---

# 🧪 Model Details

| Component | Description |
|-----------|------------|
| Model Type | GRU (Recurrent Neural Network) |
| Input | EEG Time-Series Data |
| Output | 3-Class Mood Prediction |
| Deployment | TensorFlow Lite |
| Inference | On-device |

---

# 📂 Project Structure


---

# ▶️ How to Run (Android App)



```bash
### Step 1: Clone Repository

git clone https://github.com/yourusername/eeg-mood-app.git

Step 2: Open in Android Studio

Open Android Studio

Click Open Project

Select android_app/

Step 3: Add Model File

Place:

eeg_model.tflite


inside:

app/src/main/assets/

Step 4: Build & Run

Connect Android device OR start emulator

Click Run ▶

🧠 Model Training & Deployment Steps
1️⃣ Train Model (Python)
cd model_training
python train_model.py


This generates:

eeg_model.h5

2️⃣ Convert to TensorFlow Lite
python convert_to_tflite.py


This generates:

eeg_model.tflite

3️⃣ Integrate into Android

Add .tflite file into assets/

Load using TensorFlow Lite Interpreter

Run inference on EEG input

📊 Future Enhancements

📡 Real-time EEG device integration (Bluetooth)

📈 Advanced signal preprocessing (FFT, band power extraction)

🎧 Spotify API integration

🧘 Stress trend tracking dashboard

☁ Cloud analytics backend

🔬 Multi-class emotion detection (Happy, Sad, Angry, Focused)

🎯 Use Cases

Mental health monitoring

Stress detection systems

Music therapy applications

Brain-Computer Interface research

Smart wearable integration
```

🧑‍💻 Developer

Raj Y Kangralkar
Computer Science & Artificial Intelligence
KLE Technological University

📧 rajkangralkar2003@gmail.com

🔗 linkedin.com/in/rajkangralkar

⭐ If You Like This Project

Give it a ⭐ on GitHub and contribute to advancing AI-powered mental health solutions.
