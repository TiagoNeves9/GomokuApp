package com.example.pdm2324i_gomoku_g37.screens.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.screens.main.MainActivity

class LoginActivity: ComponentActivity() {
    companion object {
        fun navigateTo(origin: MainActivity) {
            val intent = Intent(origin, LoginActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContent {
            LoginScreen(
                onBackRequested = {
                    MainActivity.navigateTo(origin = this)
                }
            )
        }
    }
}