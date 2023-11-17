package com.example.pdm2324i_gomoku_g37.screens.play

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.domain.Lobby
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.LargeCustomTitleView
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.components.ROW_DEFAULT_PADDING
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreen(
    lobbies: List<Lobby>? = null,
    navigation: NavigationHandlers = NavigationHandlers(),
) =
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CustomBar(
                text = stringResource(R.string.activity_play_top_bar_title),
                navigation = navigation,
            )
        },
        bottomBar = { GroupFooterView() }
    ) { padding ->
        CustomContainerView(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            LargeCustomTitleView(text = "List of lobbies:")

            if (lobbies != null) LobbiesList(lobbies = lobbies)
            else LoadingView()
        }
    }

@Composable
fun LobbiesList(lobbies: List<Lobby>) =
    lobbies.forEach { lobby ->
        Row(Modifier.fillMaxWidth()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(ROW_DEFAULT_PADDING),
                Arrangement.Center
            ) {
                val boardDim = lobby.rules.boardDim
                Text(
                    text = "${lobby.lobbyId}\n" +
                            "${lobby.hostUserId}\n" +
                            "Rules:" +
                            "\tBoard dimension - $boardDim x $boardDim\n" +
                            "\tOpening - ${lobby.rules.opening}\n" +
                            "\tVariant - ${lobby.rules.variant}\n"
                )
            }
        }
    }

@Composable
fun LoadingView() =
    Column {
        Text(text = "Loading...")
    }


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PlayScreenPreview() {
    val lobby0 =
        Lobby(UUID.randomUUID(), UUID.randomUUID(), Rules(15, Opening.FREESTYLE, Variant.FREESTYLE))
    val lobby1 =
        Lobby(UUID.randomUUID(), UUID.randomUUID(), Rules(15, Opening.PRO, Variant.FREESTYLE))
    val lobbies = listOf(lobby0, lobby1)
    PlayScreen(lobbies)
}