package com.example.pdm2324i_gomoku_g37.screens.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigation: NavigationHandlers = NavigationHandlers()
) =
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CustomBar(
                text = stringResource(id = R.string.activity_profile_top_bar_title),
                navigation
            )
        },
        bottomBar = { GroupFooterView()}
    ) {padding ->
        CustomContainerView(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {


        }

    }