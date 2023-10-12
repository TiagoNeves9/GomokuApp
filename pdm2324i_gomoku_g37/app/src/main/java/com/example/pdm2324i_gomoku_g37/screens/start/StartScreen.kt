package com.example.pdm2324i_gomoku_g37.screens.start

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun StartScreen(onHomeRequested: () -> Unit = {}) {
    Button(onClick = onHomeRequested) {
        Text(text = "Start")
    }
}