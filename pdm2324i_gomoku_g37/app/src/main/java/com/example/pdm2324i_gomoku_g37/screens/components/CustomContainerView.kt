package com.example.pdm2324i_gomoku_g37.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.pdm2324i_gomoku_g37.R

@Composable
fun CustomContainerView(modifier: Modifier = Modifier, content: @Composable () -> Unit = {}) =
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        content()
    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomContainerViewPreview() {
    CustomContainerView {
        Text(
            text = stringResource(id = R.string.activity_main_signup),
            fontSize = 20.sp
        )
    }
}