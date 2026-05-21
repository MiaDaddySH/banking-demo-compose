package com.junfengtech.bankingdemo

import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    fun login_isEnabledWhenUsernameAndPasswordArePresent() {
        val state = LoginUiState(
            username = "alice",
            password = "1"
        )

        assertTrue(state.isLoginEnabled)
    }

    @Test
    fun login_isDisabledWhenUsernameOrPasswordIsBlank() {
        assertFalse(LoginUiState(username = "", password = "secret").isLoginEnabled)
        assertFalse(LoginUiState(username = "alice", password = "").isLoginEnabled)
        assertFalse(LoginUiState(username = " ", password = "secret").isLoginEnabled)
        assertFalse(LoginUiState(username = "alice", password = " ").isLoginEnabled)
    }
}
