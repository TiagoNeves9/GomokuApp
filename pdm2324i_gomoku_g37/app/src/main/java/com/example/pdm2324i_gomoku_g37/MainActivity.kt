package com.example.pdm2324i_gomoku_g37

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.screens.start.StartScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StartScreen(
                onHomeRequested = {
                    HomeActivity.navigateTo(origin = this)
                }
            )
        }
    }
}