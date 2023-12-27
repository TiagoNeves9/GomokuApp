package com.example.pdm2324i_gomoku_g37.screens.play

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.domain.EnteringLobby
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.LobbyAccessError
import com.example.pdm2324i_gomoku_g37.domain.LobbyScreenState
import com.example.pdm2324i_gomoku_g37.domain.WaitingLobby
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.OutsideLobby
import com.example.pdm2324i_gomoku_g37.domain.ReadyLobby
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.domain.getOrNull
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.loaded
import com.example.pdm2324i_gomoku_g37.screens.components.BUTTON_NAME_SIZE
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.ErrorAlert
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.LoadingAlert
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.components.MediumCustomTitleView
import com.example.pdm2324i_gomoku_g37.service.GomokuLobbies


val myPadding = 10.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreen(
    lobbies: LoadState<List<WaitingLobby>?> = idle(),
    lobbyScreenState: LobbyScreenState = OutsideLobby,
    navigation: NavigationHandlers = NavigationHandlers(),
    onJoinRequested: (lobby: WaitingLobby) -> Unit = { },
    onCreateRequested: () -> Unit = { },
    onDismissLobby: () -> Unit = { }
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
                .fillMaxWidth()
                .padding(padding)
        ) {
            MediumCustomTitleView(text = "List of lobbies:")

            Row(
                Modifier.fillMaxHeight(0.85F)
            ) {
                val lobbiesList = lobbies.getOrNull()
                if (lobbiesList != null) LobbiesList(lobbies = lobbiesList, onJoinRequested)
                else LoadingView()
            }

            Row(
                Modifier
                    .fillMaxWidth(0.7F)
                    .padding(myPadding)
            ) {
                Button(
                    onClick = onCreateRequested,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.PlusOne,
                        contentDescription = "Create your own lobby"
                    )
                    Text(
                        text = "Create a new lobby",
                        fontSize = BUTTON_NAME_SIZE,
                        textAlign = TextAlign.Center
                    )
                }

                if (lobbyScreenState is WaitingLobby)
                    LoadingAlert(R.string.loading_new_game_title, R.string.loading_new_game_message, onDismissLobby)

                //if (lobbyScreenState is ReadyLobby)
                //    LoadingAlert(R.string.loading_new_game_title, R.string.loading_new_game_message)

                if (lobbyScreenState is LobbyAccessError)
                    ErrorAlert(R.string.error_join_lobby_title, R.string.error_join_lobby_message, R.string.error_retry_button_text)
            }
        }
    }

@Composable
fun LobbiesList(lobbies: List<WaitingLobby>, onJoinRequested: (lobby: WaitingLobby) -> Unit) =
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(myPadding)
    ) {
        items(lobbies) { lobby ->
            Row(
                Modifier.fillMaxWidth(),
                Arrangement.Center,
                Alignment.CenterVertically
            ) {
                val boardDim = lobby.boardDim
                Button(
                    onClick = { onJoinRequested(lobby) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = myPadding)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Join lobby"
                    )
                    Text(
                        text = "Lobby ID ${lobby.lobbyId}\n" +
                                "Host ID${lobby.hostUserId}\n",
                        fontSize = BUTTON_NAME_SIZE,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Rules:\n" +
                                "\tBoard dimension - $boardDim x $boardDim\n" +
                                "\tOpening - ${lobby.opening}\n" +
                                "\tVariant - ${lobby.variant}",
                        fontSize = BUTTON_NAME_SIZE,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

@Composable
fun LoadingView() =
    Column {
        Text(text = "Loading lobbies...")
    }


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PlayScreenPreview() {
    val waitingLobby0 = GomokuLobbies.lobbies[0]
    val waitingLobby1 = GomokuLobbies.lobbies[1]
    val lobbies = loaded(Result.success(listOf(waitingLobby0, waitingLobby1)))
    PlayScreen(lobbies)
}