package com.example.pdm2324i_gomoku_g37.screens.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm2324i_gomoku_g37.screens.main.MainActivity
import com.example.pdm2324i_gomoku_g37.screens.authors.AuthorsActivity
import com.example.pdm2324i_gomoku_g37.screens.login.LoginActivity
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme


class HomeActivity : ComponentActivity() {
    companion object {

        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, HomeActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContent {
            GomokuTheme {
                HomeScreen(
                    onAuthorsRequested = {
                        AuthorsActivity.navigateTo(origin = this)
                    }
                )
            }
        }
    }
}