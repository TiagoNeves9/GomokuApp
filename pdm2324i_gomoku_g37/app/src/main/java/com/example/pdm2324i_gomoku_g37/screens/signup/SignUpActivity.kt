package com.example.pdm2324i_gomoku_g37.screens.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.pdm2324i_gomoku_g37.GomokuDependencyProvider
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
                        user = viewModel.user,
                        username = viewModel.username,
                        password = viewModel.password,
                        passwordVisible = viewModel.passwordVisible,
                        confirmPassword = viewModel.confirmPassword,
                        confirmPasswordVisible = viewModel.confirmPasswordVisible
                    ),
                    navigation = NavigationHandlers(
                        onBackRequested = {
                            MainActivity.navigateTo(origin = this)
                        },
                        onInfoRequested = { /*InfoActivity.navigateTo(origin = this)*/ }
                    ),
                    functions = SignUpScreenFunctions(
                        onUsernameChange = viewModel::changeUsername,
                        onPasswordChange = viewModel::changePassword,
                        onPasswordVisibilityChange = viewModel::changePasswordVisible,
                        onConfirmPasswordChange = viewModel::changeConfirmPassword,
                        onConfirmPasswordVisibilityChange = viewModel::changeConfirmPasswordVisible,
                        onSignUpRequested = ::doSignUp
                    )
                )
            }
        }
    }

    private fun doSignUp() {
        viewModel.signUp { signUpResult ->
            signUpResult.getOrNull()?.let { userId ->
                HomeActivity.navigateTo(this)
                Log.v("SignUp", "Sign up successful with user: $userId")
            }
        }
    }
}