package com.example.pdm2324i_gomoku_g37.screens.play

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.Loading
import com.example.pdm2324i_gomoku_g37.domain.LobbyAccessError
import com.example.pdm2324i_gomoku_g37.domain.LobbyScreenState
import com.example.pdm2324i_gomoku_g37.domain.WaitingLobby
import com.example.pdm2324i_gomoku_g37.domain.OutsideLobby
import com.example.pdm2324i_gomoku_g37.domain.exceptionOrNull
import com.example.pdm2324i_gomoku_g37.domain.getOrNull
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.loaded
import com.example.pdm2324i_gomoku_g37.screens.components.BUTTON_NAME_SIZE
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.DEFAULT_CONTENT_PADDING
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.LoadingAlert
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.components.MediumCustomTitleView
import com.example.pdm2324i_gomoku_g37.screens.components.PLAY_SCREEN_BUTTON_MAX_WIDTH
import com.example.pdm2324i_gomoku_g37.screens.components.PLAY_SCREEN_CONTAINER_MAX_WIDTH
import com.example.pdm2324i_gomoku_g37.screens.components.ProcessError
import com.example.pdm2324i_gomoku_g37.service.GomokuLobbies

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreen(
    lobbies: LoadState<List<WaitingLobby>?> = idle(),
    lobbyScreenState: LobbyScreenState = OutsideLobby,
    navigation: NavigationHandlers = NavigationHandlers(),
    onJoinRequested: (lobby: WaitingLobby) -> Unit = { },
    onCreateRequested: () -> Unit = { },
    onDismissJoinLobby: () -> Unit = { },
    onDismissLobbies: () -> Unit = { },
    onDismissScreenState: () -> Unit = { }
) {
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
        val customContainerModifier = Modifier
            .fillMaxWidth()
            .padding(padding)
        val buttonContainerModifier = Modifier
            .fillMaxWidth(PLAY_SCREEN_BUTTON_MAX_WIDTH)
            .padding(DEFAULT_CONTENT_PADDING)

        CustomContainerView(modifier = customContainerModifier) {
            MediumCustomTitleView(text = stringResource(id = R.string.lobbies_screen_title))

            Row(modifier = Modifier.fillMaxHeight(PLAY_SCREEN_CONTAINER_MAX_WIDTH)) {
                val lobbiesList = lobbies.getOrNull()
                if (lobbiesList != null) LobbiesList(lobbies = lobbiesList, onJoinRequested)
                else Text(text = stringResource(id = R.string.no_lobbies_found))
            }

            Row(modifier = buttonContainerModifier) {
                Button(
                    onClick = onCreateRequested,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val str = stringResource(id = R.string.create_new_lobby_button_desc)
                    Icon(
                        imageVector = Icons.Default.PlusOne,
                        contentDescription = str
                    )
                    Text(
                        text = str,
                        fontSize = BUTTON_NAME_SIZE,
                        textAlign = TextAlign.Center
                    )
                }

                if (lobbies is Loading) {
                    LoadingAlert(R.string.loading_lobbies_title, R.string.loading_lobbies_message, onDismissLobbies)
                }

                lobbies.exceptionOrNull()?.let { cause ->
                    ProcessError(onDismissLobbies, cause)
                }

                if (lobbyScreenState is WaitingLobby)
                    LoadingAlert(R.string.loading_new_game_title, R.string.loading_new_game_message, onDismissJoinLobby)

                if (lobbyScreenState is LobbyAccessError)
                    LoadingAlert(R.string.error_join_lobby_title, R.string.error_join_lobby_message, onDismissScreenState)
            }
        }
    }
}

@Composable
fun LobbiesList(
    lobbies: List<WaitingLobby>,
    onJoinRequested: (lobby: WaitingLobby) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(DEFAULT_CONTENT_PADDING)
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
                        .padding(bottom = DEFAULT_CONTENT_PADDING)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = stringResource(id = R.string.join_lobby_desc)
                    )
                    Text(
                        text = stringResource(id = R.string.rules_text) + ":\n"+
                                "\t" + stringResource(id = R.string.board_dimension_text) + " - $boardDim x $boardDim\n" +
                                "\t" + stringResource(id = R.string.new_lobby_board_opening) + " - ${lobby.opening}\n" +
                                "\t" + stringResource(id = R.string.new_lobby_board_variant) + " - ${lobby.variant}",
                        fontSize = BUTTON_NAME_SIZE,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PlayScreenPreview() {
    val waitingLobby0 = GomokuLobbies.lobbies[0]
    val waitingLobby1 = GomokuLobbies.lobbies[1]
    val lobbies = loaded(Result.success(listOf(waitingLobby0, waitingLobby1)))
    PlayScreen(lobbies)
}