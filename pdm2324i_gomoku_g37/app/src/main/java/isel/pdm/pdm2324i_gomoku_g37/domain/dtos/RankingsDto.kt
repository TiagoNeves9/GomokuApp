package isel.pdm.pdm2324i_gomoku_g37.domain.dtos

import isel.pdm.pdm2324i_gomoku_g37.domain.UserStatistics
import isel.pdm.pdm2324i_gomoku_g37.service.utils.SirenModel


typealias RankingsDto = SirenModel<RankingsDtoProperties>

data class RankingsDtoProperties(val rankingList: List<UserStatistics>)

val RankingsDtoType = SirenModel.getType<RankingsDtoProperties>()