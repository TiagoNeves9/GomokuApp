package com.example.pdm2324i_gomoku_g37.screens.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.screens.main.MainActivity
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme


class LoginActivity : ComponentActivity() {
    private val viewModel by viewModels<LoginScreenViewModel>()

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, LoginActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContent {
            GomokuTheme {
                LoginScreen(
                    state = LoginScreenState(
                        viewModel.username,
                        viewModel.password,
                        viewModel.passwordVisible
                    ),
                    navigation = NavigationHandlers(
                        onBackRequested = {
                            MainActivity.navigateTo(origin = this)
                        },
                        onInfoRequested = { /*InfoActivity.navigateTo(origin = this)*/ }
                    ),
                    functions = LoginScreenFunctions(
                        onPasswordChange = viewModel::changePassword,
                        onUsernameChange = viewModel::changeUsername,
                        onPasswordVisibilityChange = viewModel::changePasswordVisible
                    ),
                    onHomeRequested = {
                        HomeActivity.navigateTo(origin = this)
                    }
                )
            }
        }
    }
}