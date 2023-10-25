package com.example.pdm2324i_gomoku_g37.screens.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.screens.main.MainActivity
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme

class SignUpActivity : ComponentActivity() {

    private val viewModel by viewModels<SignUpScreenViewModel>()

    companion object{
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
                        {}
                    ),
                    functions = SignUpScreenFunctions(
                        onUsernameChange = viewModel::changeUsername,
                        onPasswordChange = viewModel::changePassword,
                        onPasswordVisibilityChange = viewModel::changePasswordVisible,
                        onConfirmPasswordChange = viewModel::changeConfirmPassword,
                        onConfirmPasswordVisibilityChange = viewModel::changeConfirmPasswordVisible
                    ),
                    onHomeRequested = {
                        HomeActivity.navigateTo(origin = this)
                    }
                )
            }
        }
    }
}