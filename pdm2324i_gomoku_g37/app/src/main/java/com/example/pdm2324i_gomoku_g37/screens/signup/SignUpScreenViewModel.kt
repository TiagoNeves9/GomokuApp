package com.example.pdm2324i_gomoku_g37.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.Idle
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.UserInfoRepository
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.loaded
import com.example.pdm2324i_gomoku_g37.domain.loading
import com.example.pdm2324i_gomoku_g37.screens.components.EmptyConfirmPassword
import com.example.pdm2324i_gomoku_g37.screens.components.EmptyPassword
import com.example.pdm2324i_gomoku_g37.screens.components.EmptyUsername
import com.example.pdm2324i_gomoku_g37.screens.components.UnmatchedPasswords
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SignUpScreenViewModel(
    private val service: GomokuService,
    private val repository: UserInfoRepository
) : ViewModel() {

    companion object {
        fun factory(service: GomokuService, repository: UserInfoRepository) = viewModelFactory {
            initializer { SignUpScreenViewModel(service, repository) }
        }
    }

    /**
     * username
     */
    private var _username by mutableStateOf("")

    val username: String
        get() = _username

    private var _usernameErrorText by mutableStateOf("")

    val usernameErrorText: String
        get() = _usernameErrorText

    private var _isUsernameInputError by mutableStateOf(false)

    val isUsernameInputError: Boolean
        get() = _isUsernameInputError

    fun changeUsername(username: String) = viewModelScope.launch {
        _username = username.replace("\\s".toRegex(), "")
    }

    fun changeUsernameErrorText(error: String) = viewModelScope.launch {
        _usernameErrorText = error
    }

    fun changeIsUsernameInputError(isError: Boolean) = viewModelScope.launch {
        _isUsernameInputError = isError
    }

    /**
     * Password
     */

    private var _password by mutableStateOf("")

    val password: String
        get() = _password

    private var _passwordErrorText by mutableStateOf("")

    val passwordErrorText: String
        get() = _passwordErrorText

    private var _isPasswordInputError by mutableStateOf(false)

    val isPasswordInputError: Boolean
        get() = _isPasswordInputError

    private var _isPasswordVisible by mutableStateOf(false)

    val isPasswordVisible: Boolean
        get() = _isPasswordVisible

    fun changePassword(password: String) = viewModelScope.launch {
        _password = password.replace("\\s".toRegex(), "")
    }

    fun changePasswordErrorText(error: String) = viewModelScope.launch {
        _passwordErrorText = error
    }

    fun changeIsPasswordInputError(isError: Boolean) = viewModelScope.launch {
        _isPasswordInputError = isError
    }

    fun changeIsPasswordVisible(isVisible: Boolean) = viewModelScope.launch {
        _isPasswordVisible = isVisible
    }

    /**
     * Confirm Password
     */

    private var _confirmPassword by mutableStateOf("")

    val confirmPassword: String
        get() = _confirmPassword

    private var _confirmPasswordErrorText by mutableStateOf("")

    val confirmPasswordErrorText: String
        get() = _confirmPasswordErrorText

    private var _isConfirmPasswordInputError by mutableStateOf(false)

    val isConfirmPasswordInputError: Boolean
        get() = _isConfirmPasswordInputError

    private var _isConfirmPasswordVisible by mutableStateOf(false)

    val isConfirmPasswordVisible: Boolean
        get() = _isConfirmPasswordVisible

    fun changeConfirmPassword(confirmPassword: String) = viewModelScope.launch {
        _confirmPassword = confirmPassword.replace("\\s".toRegex(), "")
    }

    fun changeConfirmPasswordErrorText(error: String) = viewModelScope.launch {
        _confirmPasswordErrorText = error
    }

    fun changeIsConfirmPasswordInputError(isError: Boolean) = viewModelScope.launch {
        _isConfirmPasswordInputError = isError
    }

    fun changeIsConfirmPasswordVisible(isVisible: Boolean) = viewModelScope.launch {
        _isConfirmPasswordVisible = isVisible
    }

    /**
     * The userid that will represent the new signed in user. The user is loaded from a remote location by the
     * provided service and therefore its state is represented by a [LoadState].
     */

    private val _userInfoFlow: MutableStateFlow<LoadState<UserInfo?>> = MutableStateFlow(idle())

    val userInfoFlow: Flow<LoadState<UserInfo?>>
        get() = _userInfoFlow.asStateFlow()

    fun resetToIdle() {
        _userInfoFlow.value = idle()
    }

    fun signUp() {
        if (_userInfoFlow.value is Idle) {
            _userInfoFlow.value = loading()
            viewModelScope.launch {
                val result: Result<UserInfo> = when {
                    username.isBlank() -> Result.failure(EmptyUsername)
                    password.isBlank() -> Result.failure(EmptyPassword)
                    confirmPassword.isBlank() -> Result.failure(EmptyConfirmPassword)
                    password != confirmPassword -> Result.failure(UnmatchedPasswords)
                    else -> kotlin.runCatching {
                        val userInfo = service.signUp(username, password)
                        repository.updateUserInfo(userInfo)
                        userInfo
                    }
                }
                _userInfoFlow.value = loaded(result)
            }
        }
    }
}