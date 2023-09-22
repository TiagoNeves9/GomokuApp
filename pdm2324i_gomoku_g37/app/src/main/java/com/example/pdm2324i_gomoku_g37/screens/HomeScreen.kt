package com.example.pdm2324i_gomoku_g37.screens

import android.widget.EditText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pdm2324i_gomoku_g37.ui.theme.Pdm2324i_gomoku_g37Theme

@Composable
fun HomeScreen(){
    Pdm2324i_gomoku_g37Theme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp),
            ) {
                Row {

                    ElevatedButton(onClick = { /*TODO*/ }) {
                        Text(text = "Play")
                    }
                    ElevatedButton(onClick = { /*TODO*/ }) {
                        Text(text = "About the Game")
                    }
                    ElevatedButton(onClick = { /*TODO*/ }) {
                        Text(text = "Game Devs")

                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}