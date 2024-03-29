package com.example.pdm2324i_gomoku_g37.screens.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em


@Composable
fun LargeCustomTitleView(text: String) =
    Text(
        text = text,
        textAlign = TextAlign.Center,
        lineHeight = 1.em,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 10.dp),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleLarge
    )

@Composable
fun MediumCustomTitleView(text: String) =
    Text(
        text = text,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 10.dp),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleMedium
    )

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LargeCustomTitleViewPreview() = LargeCustomTitleView(text = "Gomoku")

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SmallCustomTitleViewPreview() = MediumCustomTitleView(text = "Gomoku")