package com.junfengtech.bankingdemo.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junfengtech.bankingdemo.ui.login.LoginAction
import com.junfengtech.bankingdemo.ui.login.LoginUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.UsernameChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        username = action.username,
                        isLoginSuccess = false,
                        loginMessage = "",
                        errorMessage = ""
                    )
                }
            }

            is LoginAction.PasswordChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        password = action.password,
                        isLoginSuccess = false,
                        loginMessage = "",
                        errorMessage = ""
                    )
                }
            }

            LoginAction.TogglePasswordVisibility -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        isPasswordVisible = !currentState.isPasswordVisible
                    )
                }
            }

            LoginAction.LoginClicked -> {
                login()
            }
        }
    }

    private fun login() {
        val currentState = _uiState.value

        if (!currentState.isLoginEnabled) return

        val username = currentState.username
        val password = currentState.password

        _uiState.update {
            it.copy(
                isLoading = true,
                loginMessage = "",
                errorMessage = ""
            )
        }

        viewModelScope.launch {
            delay(1500)

            _uiState.update {
                if (
                    username == "demo" && password == "123456"
                ) {
                    it.copy(
                        isLoading = false,
                        isLoginSuccess = true,
                        loginMessage = "Welcome, ${username}. Loading your dashboard...",
                        errorMessage = ""
                    )
                } else {
                    it.copy(
                        isLoading = false,
                        isLoginSuccess = false,
                        loginMessage = "",
                        errorMessage = "Invalid username or password."
                    )
                }
            }
        }
    }
}