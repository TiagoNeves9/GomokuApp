package isel.pdm.pdm2324i_gomoku_g37.domain

sealed class LobbyScreenState

data object EnteringLobby : LobbyScreenState()

data class ReadyLobby(val game: isel.pdm.pdm2324i_gomoku_g37.domain.Game) : LobbyScreenState()

data object OutsideLobby : LobbyScreenState()

data class LobbyAccessError(val cause: Throwable) : LobbyScreenState()

class WaitingLobby(
    val lobbyId: String,
    val hostUserId: String,
    val boardDim: Int,
    val opening: String,
    val variant: String
) : LobbyScreenState()