package com.example.pdm2324i_gomoku_g37.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pdm2324i_gomoku_g37.screens.authors.Title
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onAuthorsRequested: () -> Unit = { }) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(6.dp),
            Arrangement.Center,
            Alignment.CenterVertically,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //TODO mover Title para package de componentes reutilizaveis
                Title("Group 37")

                Row(
                    Modifier,
                    Arrangement.Center
                ) {
                    MenuButton {
                        //Text(text = "Play")
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play"
                            )
                            Text(
                                text = "Play",
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    MenuButton {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rankings"
                            )
                            Text(
                                text = "Rankings",
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Row(
                    Modifier,
                    Arrangement.Center
                ) {
                    MenuButton(onAuthorsRequested) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Face,
                                contentDescription = "Authors"
                            )
                            Text(
                                text = "Authors",
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    MenuButton() {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "About"
                            )
                            Text(
                                text = "About",
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuButton(onClick: () -> Unit = {}, content: @Composable () -> Unit) =
    ElevatedButton(
        shape = RoundedCornerShape(2.dp),
        onClick = onClick,
        //contentPadding = PaddingValues(30.dp)
        modifier = Modifier
            .size(110.dp)
            .padding(2.dp)
    ) {
        content()
    }

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun MenuButtonPreview() = MenuButton { Text(text = "Play") }

@Preview
@Composable
fun HomeScreenPreview() =
    GomokuTheme {
        HomeScreen()
    }