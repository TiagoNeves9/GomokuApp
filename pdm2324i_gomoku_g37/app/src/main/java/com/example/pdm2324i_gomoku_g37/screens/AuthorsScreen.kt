package com.example.pdm2324i_gomoku_g37.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pdm2324i_gomoku_g37.ui.theme.Pdm2324i_gomoku_g37Theme

@Composable
fun AuthorsScreen() {
    Pdm2324i_gomoku_g37Theme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            //Greeting("Group 37")
            AuthorsDisplay()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JokeScreenPreview() {
    AuthorsScreen()
}

@Composable
private fun AuthorsDisplay(modifier: Modifier = Modifier) {
    Text(
        text = "Authors:\n" +
                "\t48264\t-\tJoão Pereira\n" +
                "\t48292\t-\tTiago Neves\n" +
                "\t48333\t-\tTomás Barroso",
        modifier = modifier
    )
}