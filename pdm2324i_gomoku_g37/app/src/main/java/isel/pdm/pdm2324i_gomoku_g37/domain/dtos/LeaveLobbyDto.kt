package isel.pdm.pdm2324i_gomoku_g37.domain.dtos

import isel.pdm.pdm2324i_gomoku_g37.service.utils.SirenModel


typealias LeaveLobbyDto = SirenModel<LeaveLobbyDtoProperties>

data class LeaveLobbyDtoProperties(val waitMessage: String)

val LeaveLobbyDtoType = SirenModel.getType<LeaveLobbyDtoProperties>()