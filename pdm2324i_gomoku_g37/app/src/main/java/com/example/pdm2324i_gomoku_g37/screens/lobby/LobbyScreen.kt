package com.example.pdm2324i_gomoku_g37.screens.lobby

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.domain.Lobby
import com.example.pdm2324i_gomoku_g37.domain.toOpeningString
import com.example.pdm2324i_gomoku_g37.domain.toVariantString
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.LargeCustomTitleView
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.new_lobby.NewLobbyScreen
import com.example.pdm2324i_gomoku_g37.service.GomokuLobbies
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(
    lobby: Lobby,
    navigation: NavigationHandlers = NavigationHandlers(),
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { CustomBar(text = "Lobby", navigation = navigation) },
        bottomBar = { GroupFooterView(Color.White) },
    ) { padding ->
        val customContainerModifier = Modifier
            .padding(padding)
            .fillMaxSize()

        CustomContainerView(modifier = customContainerModifier) {
            LargeCustomTitleView(text = "Waiting for player")
            LobbyInfoView(lobby)
        }
    }
}

@Composable
private fun LobbyInfoView(lobby: Lobby) {
    Text(text = "Host: ${lobby.hostUserId}")
    Text(text = "Board dimension: ${lobby.rules.boardDim}")
    Text(text = "Opening: ${lobby.rules.opening.toOpeningString()}")
    Text(text = "Variant: ${lobby.rules.variant.toVariantString()}")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LobbyScreenPreview() {
    GomokuTheme {
        LobbyScreen(GomokuLobbies.lobbies[0])
    }
}