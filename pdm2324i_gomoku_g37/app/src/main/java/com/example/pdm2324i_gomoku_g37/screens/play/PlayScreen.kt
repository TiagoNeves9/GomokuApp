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
import com.example.pdm2324i_gomoku_g37.domain.Lobby
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.components.MediumCustomTitleView
import com.example.pdm2324i_gomoku_g37.screens.home.BUTTON_NAME_SIZE
import com.example.pdm2324i_gomoku_g37.screens.home.MenuButton
import java.util.UUID


val myPadding = 10.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreen(
    lobbies: List<Lobby>? = null,
    navigation: NavigationHandlers = NavigationHandlers(),
    onJoinRequest: () -> Unit = { },
    onCreateRequested: () -> Unit = { }
) =
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CustomBar(
                text = stringResource(R.string.activity_play_top_bar_title),
                navigation = navigation,
            )
        },
        bottomBar = { GroupFooterView(Color.White) }
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
                if (lobbies != null) LobbiesList(lobbies = lobbies)
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
            }
        }
    }

@Composable
fun LobbiesList(lobbies: List<Lobby>) =
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(myPadding)
    ) {
        items(lobbies) { lobby ->
            val guestUserId: String? = lobby.guestUserId
            val guestUserText = if (guestUserId != null) "Guest: $guestUserId" else "Waiting for second player"
            Row(
                Modifier.fillMaxWidth(),
                Arrangement.Center,
                Alignment.CenterVertically
            ) {
                val boardDim = lobby.rules.boardDim
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = myPadding)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Join lobbyyy"
                    )
                    Text(text = lobby.lobbyId, fontSize = BUTTON_NAME_SIZE, textAlign = TextAlign.Center)
                    Text(text = lobby.hostUserId, fontSize = BUTTON_NAME_SIZE, textAlign = TextAlign.Center)
                    Text(text = guestUserText, fontSize = BUTTON_NAME_SIZE, textAlign = TextAlign.Center)
                    Text(text = "Rules:" +
                                "\tBoard dimension - $boardDim x $boardDim\n" +
                                "\tOpening - ${lobby.rules.opening}\n" +
                                "\tVariant - ${lobby.rules.variant}",
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
        Text(text = "Loading...")
    }


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PlayScreenPreview() {
    val lobby0 = Lobby("1", "1", null, Rules(15, Opening.FREESTYLE, Variant.FREESTYLE))
    val lobby1 = Lobby("2", "2", "1", Rules(15, Opening.PRO, Variant.FREESTYLE))
    val lobbies = listOf(lobby0, lobby1)

    PlayScreen(lobbies)
}