package isel.pdm.pdm2324i_gomoku_g37.domain.dtos

import isel.pdm.pdm2324i_gomoku_g37.service.utils.SirenModel


typealias AboutDto = SirenModel<AboutDtoProperties>

data class AboutDtoProperties(val version: String)

val AboutDtoType = SirenModel.getType<AboutDtoProperties>()