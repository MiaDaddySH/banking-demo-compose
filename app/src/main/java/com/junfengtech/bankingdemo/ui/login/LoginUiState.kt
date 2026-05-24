package com.junfengtech.bankingdemo.ui.login

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val loginMessage: String = "",
    val errorMessage: String = ""
) {
    val isLoginEnabled: Boolean
        get() = username.isNotBlank() && password.length >= 6 && !isLoading
}