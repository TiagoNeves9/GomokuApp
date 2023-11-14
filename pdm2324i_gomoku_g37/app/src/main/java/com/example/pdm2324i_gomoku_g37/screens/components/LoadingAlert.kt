package com.example.pdm2324i_gomoku_g37.screens.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pdm2324i_gomoku_g37.ui.theme.DarkBlue50
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme

const val LoadingAlertTestTag = "LoadingAlertTestTag"

@Composable
fun LoadingAlert(
    @StringRes title: Int,
    @StringRes message: Int
) {
    LoadingAlertImpl(
        stringResource(id = title),
        stringResource(id = message),
    )
}

@Composable
fun LoadingAlertImpl(
    title: String,
    message: String,
) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = { },
        title = { Text(text = title) },
        text = {
            CustomContainerView(
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(60.dp),
                    color = DarkBlue50,
                )
                Text(
                    text = message,
                    modifier = Modifier.padding(top = 25.dp)
                )
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Warning",
                modifier = Modifier.size(42.dp)
            )
        },
        modifier = Modifier
            .testTag(LoadingAlertTestTag)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoadingAlertPreview() {
    GomokuTheme {
        LoadingAlertImpl("Create account", "We are creating your account!")
    }
}