package com.junfengtech.bankingdemo.ui.login


sealed interface LoginAction {
    data class UsernameChanged(val username: String) : LoginAction
    data class PasswordChanged(val password: String) : LoginAction
    data object TogglePasswordVisibility : LoginAction
    data object LoginClicked: LoginAction

}