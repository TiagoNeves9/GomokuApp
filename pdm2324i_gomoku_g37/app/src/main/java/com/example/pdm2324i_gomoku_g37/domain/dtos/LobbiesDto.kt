package com.example.pdm2324i_gomoku_g37.domain.dtos

import com.example.pdm2324i_gomoku_g37.domain.WaitingLobby
import com.example.pdm2324i_gomoku_g37.service.utils.SirenModel

typealias LobbiesDto = SirenModel<LobbiesDtoProperties>

data class LobbiesDtoProperties(val lobbyList: List<WaitingLobby>)

val LobbiesDtoType = SirenModel.getType<LobbiesDtoProperties>()