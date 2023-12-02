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
import com.example.pdm2324i_gomoku_g37.domain.Lobby
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(lobby: Lobby) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { GroupFooterView(Color.White) },
    ) { padding ->
        val customContainerModifier = Modifier
            .padding(padding)
            .fillMaxSize()

        CustomContainerView(modifier = customContainerModifier) {
            Text(text = "$lobby")
        }
    }
}