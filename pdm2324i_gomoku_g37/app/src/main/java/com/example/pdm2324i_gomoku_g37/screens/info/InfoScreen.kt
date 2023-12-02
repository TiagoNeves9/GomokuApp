package com.example.pdm2324i_gomoku_g37.screens.info

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers


val myPadding = 10.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    info: String? = null,
    navigation: NavigationHandlers = NavigationHandlers(),
) =
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CustomBar(
                text = stringResource(id = R.string.activity_info_top_bar_title),
                navigation = navigation
            )
        },
        bottomBar = { GroupFooterView(Color.White) }
    ) { padding ->
        CustomContainerView(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (info != null)
                Text(
                    text = info,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(myPadding),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
        }
    }

@Preview
@Composable
fun InfoScreenPreview() = InfoScreen(
    info = "The Gomoku application is in version X.Y.Z" +
            " and was made by Group 37 - Class 53D",
    navigation = NavigationHandlers(onBackRequested = { })
)
