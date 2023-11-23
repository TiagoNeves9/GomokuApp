package com.example.pdm2324i_gomoku_g37.screens.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.pdm2324i_gomoku_g37.R


val LARGE_BUTTON_FONT_SIZE = 20.sp

@Composable
fun SmallCustomButtonView(
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    ElevatedButton(
        enabled = enabled,
        onClick = onClick,
        shape = RoundedCornerShape(BUTTON_RADIUS),
        modifier = Modifier.size(width = SMALL_BUTTON_WIDTH, height = SMALL_BUTTON_HEIGHT)
    ) {
        content()
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LargeCustomButtonViewPreview() =
    SmallCustomButtonView {
        Text(
            text = stringResource(R.string.activity_main_start),
            fontSize = LARGE_BUTTON_FONT_SIZE
        )
    }