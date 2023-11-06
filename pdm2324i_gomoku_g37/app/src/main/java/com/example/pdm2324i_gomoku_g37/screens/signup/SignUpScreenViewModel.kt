package com.example.pdm2324i_gomoku_g37.screens.signup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.UserId
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.loaded
import com.example.pdm2324i_gomoku_g37.domain.loading
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.launch


class SignUpScreenViewModel(private val service: GomokuService) : ViewModel() {

    companion object {
        fun factory(service: GomokuService) = viewModelFactory {
            initializer { SignUpScreenViewModel(service) }
        }
    }

    var username by mutableStateOf("")
        private set

    fun changeUsername(newUsername: String) =
        viewModelScope.launch {
            username = newUsername
        }

    var password by mutableStateOf("")
        private set

    fun changePassword(newPassword: String) =
        viewModelScope.launch {
            password = newPassword
        }

    var passwordVisible by mutableStateOf(false)
        private set

    fun changePasswordVisible() =
        viewModelScope.launch {
            passwordVisible = !passwordVisible
        }

    var confirmPassword by mutableStateOf("")
        private set

    fun changeConfirmPassword(newConfirmPassword: String) =
        viewModelScope.launch {
            confirmPassword = newConfirmPassword
        }

    var confirmPasswordVisible by mutableStateOf(false)
        private set

    fun changeConfirmPasswordVisible() = viewModelScope.launch {
        confirmPasswordVisible = !confirmPasswordVisible
    }

    /**
     * The userid that will represent the new signed in user. The user is loaded from a remote location by the
     * provided service and therefore its state is represented by a [LoadState].
     */
    var user by mutableStateOf<LoadState<UserId>>(idle())

    fun signUp(signUpResult: (Result<UserId>) -> Unit = {}) = viewModelScope.launch {
        user = loading()
        val result: Result<UserId> = kotlin.runCatching { service.signUp(username, password) }
        user = loaded(result)
        signUpResult(result)
    }
}