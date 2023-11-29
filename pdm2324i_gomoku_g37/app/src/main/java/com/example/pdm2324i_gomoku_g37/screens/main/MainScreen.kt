package com.example.pdm2324i_gomoku_g37.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.LargeCustomButtonView
import com.example.pdm2324i_gomoku_g37.screens.components.LargeCustomTitleView
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_SCREEN_BUTTON_FONT_SIZE
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_SCREEN_DEFAULT_PADDING
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onStartEnabled: Boolean = true,
    onStartRequested: () -> Unit = {},
    navigation: NavigationHandlers = NavigationHandlers()
) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { GroupFooterView(Color.White) },
        //modifier = Modifier.fillMaxSize()
    ) { padding ->
        Image(
            painter = painterResource(R.drawable.app_background),
            contentDescription = "Background image",
            contentScale = ContentScale.FillBounds
        )

        CustomContainerView(
            modifier = Modifier
                .padding(padding)
        ) {
            val painter = painterResource(R.drawable.img_gomoku_icon)
            val imageModifier = Modifier
                .sizeIn(
                    minWidth = 300.dp,
                    minHeight = 300.dp,
                    maxWidth = 400.dp,
                    maxHeight = 400.dp
                )

            Image(
                painter = painter,
                contentDescription = null,
                alignment = Alignment.TopCenter,
                modifier = imageModifier
            )

            CustomContainerView(
                modifier = Modifier
                    .padding(40.dp)
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(20.dp))
                    .clip(shape = RoundedCornerShape(20.dp))
            ) {
                LargeCustomTitleView(text = "Gomoku")
                DescriptionContainer()
                LargeCustomButtonView(enabled = onStartEnabled, onClick = onStartRequested) {
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Start"
                    )
                }
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