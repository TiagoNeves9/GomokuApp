package com.example.pdm2324i_gomoku_g37.screens.lobby

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm2324i_gomoku_g37.domain.Lobby
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import kotlinx.parcelize.Parcelize

private const val LOBBY_EXTRA = "LobbyActivity.extra.Lobby"

class LobbyActivity : ComponentActivity() {

    companion object {
        fun navigateTo(origin: ComponentActivity, lobby: Lobby) {
            with(origin) {
                val intent = Intent(this, LobbyActivity::class.java)
                intent.putExtra(LOBBY_EXTRA, LobbyExtra(lobby))
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GomokuTheme {
                getLobbyExtra()?.let { lobbyExtra ->
                    LobbyScreen(
                        lobby = lobbyExtra.toLobby()
                    )
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun getLobbyExtra(): LobbyExtra? =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
            intent.getParcelableExtra(LOBBY_EXTRA, LobbyExtra::class.java)
        else
            intent.getParcelableExtra(LOBBY_EXTRA)
}

@Parcelize
private data class LobbyExtra(
    val lobbyId: String,
    val hostUserId: String,
    val guestUserId: String?,
    val boardDim: Int,
    val gameOpening: Opening,
    val gameVariant: Variant
) : Parcelable {
    constructor(lobby: Lobby) : this(
        lobby.lobbyId,
        lobby.hostUserId,
        lobby.guestUserId,
        lobby.rules.boardDim,
        lobby.rules.opening,
        lobby.rules.variant
    )
}

private fun LobbyExtra.toLobby() = Lobby(
        lobbyId, hostUserId, guestUserId, Rules(boardDim, gameOpening, gameVariant)
    )