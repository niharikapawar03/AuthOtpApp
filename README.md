# OTP App Notes

## 1. OTP generation and expiry logic
- `OtpManager.generateOtp(email)` creates a 6-digit numeric OTP, stores it with `generatedAtMillis`, and resets remaining attempts to 3. A new OTP overwrites any existing OTP for the same email.
- `validateOtp(email, otp)` checks in order: exists, not expired (60 seconds), attempts remaining, and match. On failure it returns a specific result and decrements attempts where an OTP exists.

## 2. Data structure used and why
- `MutableMap<String, OtpData>` keyed by email is used for O(1) access and to keep OTPs isolated per user without persistence.

## 3. External SDK chosen and why
- Firebase Analytics was chosen to satisfy the SDK requirement and to log events without adding UI or architectural complexity.

## 4. GPT assistance vs my understanding
- GPT helped scaffold the initial files and draft the OTP and logging logic.
- I reviewed the flow, validated each requirement (expiry, attempts, logging, timer), and integrated Firebase Analytics with the correct package name.

## Setup instructions
1. Open the project in Android Studio: `OtpApp/`.
2. Ensure `app/google-services.json` exists (already added).
3. Sync Gradle and run the app on an emulator/device.
