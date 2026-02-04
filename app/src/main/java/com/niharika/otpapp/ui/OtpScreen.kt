package com.niharika.otpapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.niharika.otpapp.viewmodel.AuthState
import com.niharika.otpapp.viewmodel.AuthViewModel

@Composable
fun OtpScreen(viewModel: AuthViewModel) {
    var otpInput by rememberSaveable { androidx.compose.runtime.mutableStateOf("") }
    val authState = viewModel.authState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Verify OTP",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Check the OTP below (demo) and enter it to continue.",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (viewModel.lastGeneratedOtp != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "OTP: ${viewModel.lastGeneratedOtp}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = otpInput,
                    onValueChange = { otpInput = it },
                    label = { Text("OTP") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.validateOtp(otpInput) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Verify")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.sendOtp() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Resend OTP")
                }

                if (authState is AuthState.Error) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = authState.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
