package com.example.pdm2324i_gomoku_g37.domain.dtos

import com.example.pdm2324i_gomoku_g37.domain.UserStatistics
import com.example.pdm2324i_gomoku_g37.service.utils.SirenModel

typealias RankingsDto = SirenModel<RankingsDtoProperties>

data class RankingsDtoProperties(val rankingList: List<UserStatistics>)

val RankingsDtoType = SirenModel.getType<RankingsDtoProperties>()