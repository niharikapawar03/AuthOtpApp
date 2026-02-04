package com.niharika.otpapp.viewmodel

sealed class AuthState {
    data object Idle : AuthState()
    data object OtpSent : AuthState()
    data object LoggedIn : AuthState()
    data class Error(val message: String) : AuthState()
}
