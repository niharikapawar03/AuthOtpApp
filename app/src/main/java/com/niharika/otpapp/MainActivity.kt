package com.niharika.otpapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niharika.otpapp.analytics.AnalyticsLogger
import com.niharika.otpapp.ui.LoginScreen
import com.niharika.otpapp.ui.OtpScreen
import com.niharika.otpapp.ui.SessionScreen
import com.niharika.otpapp.viewmodel.AuthState
import com.niharika.otpapp.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                AuthRoot()
            }
        }
    }
}

@Composable
private fun AuthRoot() {
    val context = LocalContext.current.applicationContext
    val logger = AnalyticsLogger(context)
    val factory = AuthViewModelFactory(logger)
    val viewModel: AuthViewModel = viewModel(factory = factory)

    when (val state = viewModel.authState) {
        AuthState.Idle -> LoginScreen(viewModel)
        AuthState.OtpSent -> OtpScreen(viewModel)
        AuthState.LoggedIn -> SessionScreen(viewModel)
        is AuthState.Error -> {
            if (viewModel.email.isBlank()) {
                LoginScreen(viewModel)
            } else {
                OtpScreen(viewModel)
            }
        }
    }
}

private class AuthViewModelFactory(
    private val analyticsLogger: AnalyticsLogger
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(analyticsLogger = analyticsLogger) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
