package com.niharika.otpapp.data

data class OtpData(
    val otp: String,
    val generatedAtMillis: Long,
    val remainingAttempts: Int
)

sealed class OtpValidationResult {
    data object Success : OtpValidationResult()
    data object NotFound : OtpValidationResult()
    data object Expired : OtpValidationResult()
    data object NoAttempts : OtpValidationResult()
    data object Mismatch : OtpValidationResult()
}

class OtpManager(
    private val timeProviderMillis: () -> Long = { System.currentTimeMillis() }
) {
    private val otpStore: MutableMap<String, OtpData> = mutableMapOf()

    fun generateOtp(email: String): String {
        val otp = (100000..999999).random().toString()
        val now = timeProviderMillis()

        otpStore[email] = OtpData(
            otp = otp,
            generatedAtMillis = now,
            remainingAttempts = MAX_ATTEMPTS
        )
        return otp
    }

    fun validateOtp(email: String, otpInput: String): OtpValidationResult {
        val current = otpStore[email] ?: return OtpValidationResult.NotFound

        val now = timeProviderMillis()

        if (now - current.generatedAtMillis > OTP_EXPIRY_MILLIS) {
            otpStore[email] = current.copy(
                remainingAttempts = (current.remainingAttempts - 1).coerceAtLeast(0)
            )
            return OtpValidationResult.Expired
        }

        if (current.remainingAttempts <= 0) {
            return OtpValidationResult.NoAttempts
        }

        if (current.otp != otpInput) {
            otpStore[email] = current.copy(
                remainingAttempts = (current.remainingAttempts - 1).coerceAtLeast(0)
            )
            return OtpValidationResult.Mismatch
        }

        return OtpValidationResult.Success
    }

    companion object {
        private const val MAX_ATTEMPTS = 3
        private const val OTP_EXPIRY_MILLIS = 60_000L
    }
}
