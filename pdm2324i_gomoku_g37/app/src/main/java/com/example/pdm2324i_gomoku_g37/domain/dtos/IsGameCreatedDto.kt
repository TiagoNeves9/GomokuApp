package com.example.pdm2324i_gomoku_g37.domain.dtos

import com.example.pdm2324i_gomoku_g37.service.utils.SirenModel

typealias IsGameCreatedDto = SirenModel<IsGameCreatedDtoProperties>

data class IsGameCreatedDtoProperties(val waitMessage: String)

val IsGameCreatedDtoType = SirenModel.getType<IsGameCreatedDtoProperties>()