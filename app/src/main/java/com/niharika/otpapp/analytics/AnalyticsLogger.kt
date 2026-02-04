package com.niharika.otpapp.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent

class AnalyticsLogger(context: Context) {
    private val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    fun logOtpGenerated(email: String) {
        analytics.logEvent("otp_generated") {
            param("email", email)
        }
    }

    fun logOtpValidationSuccess(email: String) {
        analytics.logEvent("otp_validation_success") {
            param("email", email)
        }
    }

    fun logOtpValidationFailure(email: String) {
        analytics.logEvent("otp_validation_failure") {
            param("email", email)
        }
    }

    fun logLogout(email: String) {
        analytics.logEvent("logout") {
            param("email", email)
        }
    }
}
