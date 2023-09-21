package com.example.pdm2324i_gomoku_g37.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pdm2324i_gomoku_g37.ui.theme.LightBlue
import com.example.pdm2324i_gomoku_g37.ui.theme.Pdm2324i_gomoku_g37Theme
import com.example.pdm2324i_gomoku_g37.ui.theme.SoftRed


@Composable
fun AuthorsScreen() {
    Pdm2324i_gomoku_g37Theme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            //Greeting("Group 37")
            //AuthorsDisplay()
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp),
            ) {
                Text(
                    text = "Group 37",
                    fontSize = 35.sp,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { println("Prev pressed") }, //TODO
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SoftRed,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(15.dp),
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Prev",
                            fontSize = 10.sp,
                        )
                    }

                    ElevatedCard(
                        colors = CardDefaults.cardColors(
                            containerColor = LightBlue,
                        ),
                        shape = RoundedCornerShape(5.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        ),
                        modifier = Modifier
                            .padding(16.dp)
                            .size(width = 200.dp, height = 300.dp)
                    ) {
                        AuthorsDisplay()
                    }

                    Button(
                        onClick = { println("Next pressed") }, //TODO
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SoftRed,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(15.dp),
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Next",
                            fontSize = 10.sp,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthorsScreenPreview() {
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