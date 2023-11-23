package com.example.pdm2324i_gomoku_g37.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.CustomFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.SmallCustomButtonView
import com.example.pdm2324i_gomoku_g37.screens.components.LargeCustomTitleView
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_SCREEN_BUTTON_FONT_SIZE
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_SCREEN_DEFAULT_PADDING
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onStartEnabled: Boolean = true,
    onStartRequested: () -> Unit = {},
) =
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { GroupFooterView() }
    ) { padding ->
        CustomContainerView(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LargeCustomTitleView(text = "Gomoku")
            Image(
                painter = painterResource(R.drawable.img_gomoku_icon),
                contentDescription = null
            )
            DescriptionContainer()
            SmallCustomButtonView(enabled = onStartEnabled, onClick = onStartRequested) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    /*Icon(
                        imageVector = Icons.Default.Start,
                        contentDescription = "Start"
                    )*/
                    Text(
                        text = stringResource(R.string.activity_main_start),
                        fontSize = MAIN_SCREEN_BUTTON_FONT_SIZE
                    )
                }
            }
        }
    }

@Composable
private fun DescriptionContainer() =
    CustomContainerView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = MAIN_SCREEN_DEFAULT_PADDING)
    ) {
        Text(
            text = stringResource(R.string.activity_main_description),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() =
    GomokuTheme {
        MainScreen()
    }