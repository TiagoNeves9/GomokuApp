package com.example.pdm2324i_gomoku_g37.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.screens.main.MainActivity
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme

class LoginActivity: ComponentActivity() {
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
                    navigation = NavigationHandlers(
                        onBackRequested = {
                            MainActivity.navigateTo(origin = this)
                        },
                        onInfoRequested = {}
                    ),
                    onHomeRequested = {
                        HomeActivity.navigateTo(origin = this)
                    }
                )
            }
        }
    }
}