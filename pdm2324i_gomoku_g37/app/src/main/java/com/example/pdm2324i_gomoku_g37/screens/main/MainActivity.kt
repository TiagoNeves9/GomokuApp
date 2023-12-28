package com.example.pdm2324i_gomoku_g37.screens.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.example.pdm2324i_gomoku_g37.GomokuDependenciesContainer
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.domain.Idle
import com.example.pdm2324i_gomoku_g37.domain.Loaded
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.getOrNull
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.screens.components.ErrorAlert
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.screens.login.LoginActivity
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val dependencies by lazy {
        application as GomokuDependenciesContainer
    }

    private val viewModel by viewModels<MainScreenViewModel> {
        MainScreenViewModel.factory(dependencies.userInfoRepository)
    }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, MainActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.userInfoFlow.collect {
                if (it is Loaded && it.value.isSuccess) {
                    doNavigation(it.getOrNull())
                    viewModel.resetToIdle()
                }
            }
        }

        setContent {
            val currentUserInfo by viewModel.userInfoFlow.collectAsState(initial = idle())
            GomokuTheme {
                MainScreen(
                    onStartEnabled = currentUserInfo is Idle,
                    onStartRequested = { viewModel.fetchUserInfo() }
                )
                currentUserInfo.let {
                    if (it is Loaded && it.value.isFailure)
                        ErrorAlert(
                            title = R.string.failed_to_read_preferences_error_dialog_title,
                            message = R.string.failed_to_read_preferences_error_dialog_text,
                            buttonText = R.string.failed_to_read_preferences_error_dialog_ok_button,
                            onDismiss = { viewModel.resetToIdle() },
                        )
                }
            }
        }
    }

    private fun doNavigation(userInfo: UserInfo?) {
        if (userInfo == null) LoginActivity.navigateTo(this)
        else HomeActivity.navigateTo(origin = this, userInfo = userInfo)
    }
}