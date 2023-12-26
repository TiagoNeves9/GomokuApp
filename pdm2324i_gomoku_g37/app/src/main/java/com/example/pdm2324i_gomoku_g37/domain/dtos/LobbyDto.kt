package com.example.pdm2324i_gomoku_g37.domain.dtos

import com.example.pdm2324i_gomoku_g37.domain.WaitingLobby
import com.example.pdm2324i_gomoku_g37.service.utils.SirenModel

typealias LobbyDto = SirenModel<LobbyDtoProperties>

data class LobbyDtoProperties(
    val lobbyId: String,
    val hostUserId: String,
    val boardDim: Int,
    val opening: String,
    val variant: String
)

val LobbyDtoType = SirenModel.getType<LobbyDtoProperties>()

fun LobbyDtoProperties.toWaitingLobby() = WaitingLobby(
    lobbyId, hostUserId, boardDim, opening, variant
)