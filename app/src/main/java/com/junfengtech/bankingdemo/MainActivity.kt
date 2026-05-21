package com.junfengtech.bankingdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.input.VisualTransformation
import com.junfengtech.bankingdemo.ui.theme.BankingDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BankingDemoTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {
    var uiState by remember { mutableStateOf(LoginUiState()) }

    fun handleAction(action: LoginAction) {
        uiState = when (action) {
            is LoginAction.UsernameChanged -> {
                uiState.copy(
                    username = action.username,
                    loginMessage = "",
                    errorMessage = ""
                )
            }

            is LoginAction.PasswordChanged -> {
                uiState.copy(
                    password = action.password,
                    loginMessage = "",
                    errorMessage = ""
                )
            }

            LoginAction.TogglePasswordVisibility -> {
                uiState.copy(
                    isPasswordVisible = !uiState.isPasswordVisible
                )
            }
            LoginAction.LoginClicked -> {
                if (uiState.username == "demo") {
                    uiState.copy(
                        loginMessage = "Welcome, ${uiState.username}. Loading your dashboard...",
                        errorMessage = ""
                    )
                } else {
                    uiState.copy(
                        loginMessage = "",
                        errorMessage = "Invalid username. Try demo."
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome back",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Secure access to your mobile banking",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = uiState.username,
            onValueChange = { newUsername ->
                handleAction(LoginAction.UsernameChanged(newUsername))
            },
            label = { Text("Username") },
            placeholder = { Text("Enter your username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { newPassword ->
                handleAction(LoginAction.PasswordChanged(newPassword))
            },
            label = { Text("Password") },
            placeholder = { Text("Enter your password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (uiState.isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        handleAction(LoginAction.TogglePasswordVisibility)
                    }
                ) {
                    Icon(
                        imageVector = if (uiState.isPasswordVisible) {
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        },
                        contentDescription = if (uiState.isPasswordVisible) {
                            "Hide password"
                        } else {
                            "Show password"
                        }
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                handleAction(LoginAction.LoginClicked)
            },
            enabled = uiState.isLoginEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.loginMessage.isNotEmpty()) {
            Text(
                text = uiState.loginMessage,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (uiState.errorMessage.isNotEmpty()) {
            Text(
                text = uiState.errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val loginMessage: String = "",
    val errorMessage: String = ""
) {
    val isLoginEnabled: Boolean
        get() = username.isNotBlank() && password.length >= 6
}

sealed interface LoginAction {
    data class UsernameChanged(val username: String) : LoginAction
    data class PasswordChanged(val password: String) : LoginAction
    data object TogglePasswordVisibility : LoginAction
    data object LoginClicked: LoginAction
    
}