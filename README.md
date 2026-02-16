🔐 OTP Verification App

A simple Android application that demonstrates secure OTP (One-Time Password) generation and validation logic, including expiry handling, attempt limits, and Firebase Analytics integration.

📌 Features
✅ 6-digit OTP generation
✅ OTP expiry after 60 seconds
✅ Maximum 3 validation attempts
✅ Automatic OTP overwrite for same email
✅ Event logging using Firebase Analytics
✅ Clean and simple implementation (no backend persistence)

🧠 OTP Logic Implementation
1️⃣ OTP Generation
OtpManager.generateOtp(email) performs the following:
Generates a 6-digit numeric OTP
Stores:
otp
generatedAtMillis
remainingAttempts = 3
If an OTP already exists for the email, it overwrites the previous one
2️⃣ OTP Validation
validateOtp(email, otp) checks in the following order:
OTP exists
OTP not expired (valid for 60 seconds)
Remaining attempts available
OTP match
Validation Results:
❌ Not found
⏳ Expired
🚫 Attempts exhausted
❌ Incorrect OTP (attempts decrease)
✅ Success
Attempts are decremented only if OTP exists and validation fails.

🗂 Data Structure Used
MutableMap<String, OtpData>
Why this structure?
🔹 Keyed by email
🔹 Provides O(1) access time
🔹 Keeps OTPs isolated per user
🔹 No persistence required (in-memory storage)
🔹 Simple and efficient for demo purposes

📊 External SDK Integration
🔥 Firebase Analytics

Firebase Analytics was integrated to:
Log OTP generation events
Log OTP validation attempts
Track success/failure cases
Fulfill the external SDK requirement
Why Firebase Analytics?
Lightweight integration
No additional UI complexity
Production-ready logging system
Industry-standard analytics tool

🤖 GPT Assistance vs My Understanding
GPT Helped With:
Initial project scaffolding
Drafting OTP logic structure
Firebase Analytics setup guidance
My Contribution:
Reviewed and verified complete OTP flow
Implemented expiry logic (60 seconds)
Implemented attempt restriction (3 tries)
Integrated Firebase Analytics with correct package name
Ensured logging and timer functionality works properly

⚙️ Setup Instructions
1️⃣ Open Project
Open the project in Android Studio:
OtpApp/
2️⃣ Firebase Configuration
Ensure the following file exists:
app/google-services.json

3️⃣ Sync & Run
Sync Gradle
Run the app on emulator or physical device

⏱ OTP Rules Summary
Rule	Value
OTP Length	6 digits
Expiry Time	60 seconds
Max Attempts	3
Storage Type	In-memory (MutableMap)
🚀 Future Improvements
Backend-based OTP storage
SMS/email OTP delivery
Encryption for enhanced security
Room database integration
UI improvements with Material Design

📌 Tech Stack

Kotlin
Android SDK
Firebase Analytics

Android Studio
