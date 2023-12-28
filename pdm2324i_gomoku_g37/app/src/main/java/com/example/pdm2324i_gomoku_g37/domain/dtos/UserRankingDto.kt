package com.example.pdm2324i_gomoku_g37.domain.dtos

import com.example.pdm2324i_gomoku_g37.domain.UserStatistics
import com.example.pdm2324i_gomoku_g37.service.utils.SirenModel

typealias UserRankingDto = SirenModel<UserRankingDtoProperties>

data class UserRankingDtoProperties(val userRanking: UserStatistics)

val UserRankingDtoType = SirenModel.getType<UserRankingDtoProperties>()