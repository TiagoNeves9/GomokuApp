package com.example.pdm2324i_gomoku_g37.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.helpers.MainScreenTestTags.MainScreenDisplayTestTag
import com.example.pdm2324i_gomoku_g37.helpers.MainScreenTestTags.StartButtonTestTag
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.LargeCustomButtonView
import com.example.pdm2324i_gomoku_g37.screens.components.LargeCustomTitleView
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_IMAGE_MAX_HEIGHT
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_IMAGE_MAX_WIDTH
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_IMAGE_MIN_HEIGHT
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_IMAGE_MIN_WIDTH
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_ROUNDED_CORNER_SHAPE
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_SCREEN_CONTAINER_PADDING
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_SCREEN_DEFAULT_PADDING
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onStartEnabled: Boolean = true,
    onStartRequested: () -> Unit = {},
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { GroupFooterView() },
        modifier = Modifier.testTag(MainScreenDisplayTestTag)
    ) { padding ->
        Image(
            painter = painterResource(R.drawable.app_background),
            contentDescription = "Background image",
            contentScale = ContentScale.FillBounds
        )

        CustomContainerView(modifier = Modifier.padding(padding)) {
            val painter = painterResource(R.drawable.img_gomoku_icon)
            val imageModifier = Modifier.sizeIn(
                minWidth = MAIN_IMAGE_MIN_WIDTH,
                minHeight = MAIN_IMAGE_MIN_HEIGHT,
                maxWidth = MAIN_IMAGE_MAX_WIDTH,
                maxHeight = MAIN_IMAGE_MAX_HEIGHT
            )

            val customContainerModifier = Modifier
                .padding(MAIN_SCREEN_CONTAINER_PADDING)
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(MAIN_ROUNDED_CORNER_SHAPE)
                )
                .clip(shape = RoundedCornerShape(MAIN_ROUNDED_CORNER_SHAPE))

            Image(
                painter = painter,
                contentDescription = null,
                alignment = Alignment.TopCenter,
                modifier = imageModifier
            )

            CustomContainerView(modifier = customContainerModifier) {
                LargeCustomTitleView(text = "Gomoku")
                DescriptionContainer()
                LargeCustomButtonView(
                    modifier = Modifier.testTag(StartButtonTestTag),
                    enabled = onStartEnabled,
                    onClick = onStartRequested
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.surfaceVariant,
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
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() =
    GomokuTheme {
        MainScreen()
    }