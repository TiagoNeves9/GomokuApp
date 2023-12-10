package com.example.pdm2324i_gomoku_g37.domain

sealed class LobbyScreenState

data object EnteringLobby : LobbyScreenState()

data class ReadyLobby(val game: Game) : LobbyScreenState()

data object OutsideLobby : LobbyScreenState()

data class LobbyAccessError(val cause: Throwable) : LobbyScreenState()

class WaitingLobby(
    val lobbyId: String,
    val hostUserId: String,
    val guestUserId: String?,
    val rules: Rules
) : LobbyScreenState()