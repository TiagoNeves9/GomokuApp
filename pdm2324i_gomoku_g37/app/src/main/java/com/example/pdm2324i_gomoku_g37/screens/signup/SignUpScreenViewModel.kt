package com.example.pdm2324i_gomoku_g37.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SignUpScreenViewModel: ViewModel() {
    var username by mutableStateOf("")
        private set

    fun changeUsername(newUsername: String) {
        viewModelScope.launch {
            username = newUsername
        }
    }

    var password by mutableStateOf("")
        private set

    fun changePassword(newPassword: String) {
        viewModelScope.launch {
            password = newPassword
        }
    }

    var passwordVisible by mutableStateOf(false)
        private set

    fun changePasswordVisible() {
        viewModelScope.launch {
            passwordVisible = !passwordVisible
        }
    }

    var confirmPassword by mutableStateOf("")
        private set

    fun changeConfirmPassword(newConfirmPassword: String) {
        viewModelScope.launch {
            confirmPassword = newConfirmPassword
        }
    }

    var confirmPasswordVisible by mutableStateOf(false)
        private set

    fun changeConfirmPasswordVisible() {
        viewModelScope.launch {
            confirmPasswordVisible = !confirmPasswordVisible
        }
    }
}