package com.example.pdm2324i_gomoku_g37.domain

sealed class LobbyScreenState

data object EnteringLobby : LobbyScreenState()

data class WaitingLobby(
    val lobbyId: String,
    val hostUserId: String,
    val guestUserId: String?,
    val rules: Rules
) : LobbyScreenState()

data class InsideLobby(
    val lobbyId: String,
    val hostPlayer: Player,
    val guestPlayer: Player,
    val rules: Rules
) : LobbyScreenState()

data object OutsideLobby : LobbyScreenState()

data class LobbyAccessError(val cause: Throwable) : LobbyScreenState()

class Lobby(
    val lobbyId: String,
    val hostUserId: String,
    val guestUserId: String?,
    val rules: Rules
)