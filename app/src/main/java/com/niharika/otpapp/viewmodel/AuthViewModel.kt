package com.niharika.otpapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niharika.otpapp.analytics.AnalyticsLogger
import com.niharika.otpapp.data.OtpManager
import com.niharika.otpapp.data.OtpValidationResult
import kotlinx.coroutines.launch
import android.util.Log

class AuthViewModel(
    private val otpManager: OtpManager = OtpManager(),
    private val analyticsLogger: AnalyticsLogger
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var authState: AuthState by mutableStateOf(AuthState.Idle)
        private set

    var sessionStartTimeMillis: Long? by mutableStateOf(null)
        private set

    var lastGeneratedOtp: String? by mutableStateOf(null)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun sendOtp() {
        viewModelScope.launch {
            if (email.isBlank()) {
                authState = AuthState.Error("Email is required")
                return@launch
            }
            val otp = otpManager.generateOtp(email)
            lastGeneratedOtp = otp

            Log.d("OTP_DEBUG", "Generated OTP for $email : $otp")

            analyticsLogger.logOtpGenerated(email)
            authState = AuthState.OtpSent

        }
    }

    fun validateOtp(otpInput: String) {
        viewModelScope.launch {
            if (email.isBlank()) {
                authState = AuthState.Error("Email is required")
                return@launch
            }

            when (otpManager.validateOtp(email, otpInput)) {
                OtpValidationResult.Success -> {
                    Log.d("OTP_DEBUG", "OTP validated successfully for $email")
                    analyticsLogger.logOtpValidationSuccess(email)
                    sessionStartTimeMillis = System.currentTimeMillis()
                    authState = AuthState.LoggedIn
                }
                OtpValidationResult.NotFound -> {
                    analyticsLogger.logOtpValidationFailure(email)
                    authState = AuthState.Error("OTP not found")
                }
                OtpValidationResult.Expired -> {
                    analyticsLogger.logOtpValidationFailure(email)
                    authState = AuthState.Error("OTP expired")
                }
                OtpValidationResult.NoAttempts -> {
                    analyticsLogger.logOtpValidationFailure(email)
                    authState = AuthState.Error("No attempts remaining")
                }
                OtpValidationResult.Mismatch -> {
                    analyticsLogger.logOtpValidationFailure(email)
                    authState = AuthState.Error("Invalid OTP")
                }
            }
        }
    }

    fun logout() {
        analyticsLogger.logLogout(email)
        sessionStartTimeMillis = null
        lastGeneratedOtp = null
        authState = AuthState.Idle
    }

}
