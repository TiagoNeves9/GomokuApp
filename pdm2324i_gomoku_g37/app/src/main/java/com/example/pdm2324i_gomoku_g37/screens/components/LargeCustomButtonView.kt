package com.example.pdm2324i_gomoku_g37.screens.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pdm2324i_gomoku_g37.R

@Composable
fun LargeCustomButtonView(onClick: () -> Unit = {}, content: @Composable () -> Unit = {}) {
    Button(
        shape = RoundedCornerShape(4.dp),
        onClick = onClick,
        modifier = Modifier
            .size(width = 200.dp, height = 70.dp)
    ) {
        content()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LargeCustomButtonViewPreview() {
    LargeCustomButtonView {
        Text(
            text = stringResource(id = R.string.activity_main_signup),
            fontSize = 20.sp
        )
    }
}