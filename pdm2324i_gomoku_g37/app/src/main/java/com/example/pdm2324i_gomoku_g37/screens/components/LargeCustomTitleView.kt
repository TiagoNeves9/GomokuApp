package com.example.pdm2324i_gomoku_g37.screens.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LargeCustomTitleView(text: String) =
    Text(
        text = text,
        modifier = Modifier.padding(bottom = 15.dp),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground
    )

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LargeCustomTitleViewPreview() {
    LargeCustomTitleView(text = "Gomoku")
}