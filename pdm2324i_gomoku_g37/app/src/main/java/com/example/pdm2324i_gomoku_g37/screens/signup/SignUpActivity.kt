package com.example.pdm2324i_gomoku_g37.screens.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.pdm2324i_gomoku_g37.GomokuDependencyProvider
import com.example.pdm2324i_gomoku_g37.domain.Loaded
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.getOrNull
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.screens.main.MainActivity
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme


class SignUpActivity : ComponentActivity() {

    /**
     * The application's dependency provider.
     */
    private val dependencies by lazy { application as GomokuDependencyProvider }

    /**
     * The view model for the sign up screen of the Gomoku app.
     */
    private val viewModel by viewModels<SignUpScreenViewModel> {
        SignUpScreenViewModel.factory(dependencies.gomokuService)
    }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, SignUpActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContent {
            GomokuTheme {
                SignUpScreen(
                    state = SignUpScreenState(
                        user = viewModel.userId,
                        username = viewModel.username,
                        usernameErrorText = viewModel.usernameErrorText,
                        isUsernameInputError = viewModel.isUsernameInputError,
                        password = viewModel.password,
                        passwordErrorText = viewModel.passwordErrorText,
                        isPasswordInputError = viewModel.isPasswordInputError,
                        passwordVisible = viewModel.isPasswordVisible,
                        confirmPassword = viewModel.confirmPassword,
                        confirmPasswordErrorText = viewModel.confirmPasswordErrorText,
                        isConfirmPasswordInputError = viewModel.isConfirmPasswordInputError,
                        confirmPasswordVisible = viewModel.isConfirmPasswordVisible
                    ),
                    navigation = NavigationHandlers(
                        onBackRequested = {
                            MainActivity.navigateTo(origin = this)
                        },
                        onInfoRequested = { /*InfoActivity.navigateTo(origin = this)*/ }
                    ),
                    functions = SignUpScreenFunctions(
                        onUsernameChange = viewModel::changeUsername,
                        onUsernameErrorTextChange = viewModel::changeUsernameErrorText,
                        onIsUsernameInputErrorChange = viewModel::changeIsUsernameInputError,
                        onPasswordChange = viewModel::changePassword,
                        onPasswordErrorTextChange = viewModel::changePasswordErrorText,
                        onIsPasswordInputErrorChange = viewModel::changeIsPasswordInputError,
                        onPasswordVisibilityChange = viewModel::changeIsPasswordVisible,
                        onConfirmPasswordChange = viewModel::changeConfirmPassword,
                        onConfirmPasswordErrorTextChange = viewModel::changeConfirmPasswordErrorText,
                        onIsConfirmPasswordInputErrorChange = viewModel::changeIsConfirmPasswordInputError,
                        onConfirmPasswordVisibilityChange = viewModel::changeIsConfirmPasswordVisible,
                        onSignUpRequested = ::doSignUp
                    )
                )
            }
        }
    }

    private fun doSignUp() {
        viewModel.signUp { signUpResult ->
            if (signUpResult is Loaded) {
                signUpResult.getOrNull()?.let { userId ->
                    val user = UserInfo(userId.id, viewModel.username)
                    HomeActivity.navigateTo(this, user)
                }
            }
        }
    }
}