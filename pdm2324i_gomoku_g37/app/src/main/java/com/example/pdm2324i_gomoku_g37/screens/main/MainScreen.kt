package com.example.pdm2324i_gomoku_g37.screens.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pdm2324i_gomoku_g37.ui.theme.Pdm2324i_gomoku_g37Theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(onHomeRequested: () -> Unit = {}) {
    Pdm2324i_gomoku_g37Theme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { padding ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                Button(onClick = onHomeRequested) {
                    Text(text = "Start")
                }
            }
        }
    }
}