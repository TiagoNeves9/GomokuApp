package com.example.pdm2324i_gomoku_g37.screens.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.ui.theme.Grey90


val LARGE_BUTTON_FONT_SIZE = 20.sp

@Composable
fun LargeCustomButtonView(
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    FilledTonalButton(
        enabled = enabled,
        shape = CircleShape,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .padding(top = 10.dp, bottom = 15.dp)
            .size(size = 50.dp)
    ) {
        content()
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LargeCustomButtonViewPreview() =
    LargeCustomButtonView {
        Text(
            text = stringResource(R.string.activity_main_start),
            fontSize = LARGE_BUTTON_FONT_SIZE
        )
    }