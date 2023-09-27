package com.example.pdm2324i_gomoku_g37.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pdm2324i_gomoku_g37.domain.BOARD_CELL_SIZE
import com.example.pdm2324i_gomoku_g37.ui.theme.Pdm2324i_gomoku_g37Theme


@Composable
fun GameScreen() {
    Pdm2324i_gomoku_g37Theme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SymbolAxisView()
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    NumberAxisView()
                    PiecesView()
                    NumberAxisView()
                }
                SymbolAxisView()
                Spacer(modifier = Modifier.padding(vertical = BOARD_CELL_SIZE.dp))
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Group 37 - XPTO")
                }
            }
        }
    }
}

@Preview
@Composable
fun GameScreenPreview() {
    GameScreen()
}