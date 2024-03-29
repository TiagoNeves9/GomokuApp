package com.example.pdm2324i_gomoku_g37.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pdm2324i_gomoku_g37.R


val CUSTOM_FOOTER_PADDING = 15.dp

@Composable
fun CustomFooterView(modifier: Modifier = Modifier, content: @Composable () -> Unit = {}) =
    CustomContainerView(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = CUSTOM_FOOTER_PADDING)
    ) {
        content()
    }

@Composable
fun GroupFooterView() =
    CustomFooterView {
        Text(
            text = stringResource(R.string.activity_main_footer),
            textAlign = TextAlign.Center
        )
    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomFooterViewPreview() =
    CustomFooterView {
        Text(
            text = stringResource(R.string.activity_main_footer),
            textAlign = TextAlign.Center
        )
    }