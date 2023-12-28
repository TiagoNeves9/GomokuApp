package isel.pdm.pdm2324i_gomoku_g37.domain.dtos

import isel.pdm.pdm2324i_gomoku_g37.service.utils.SirenModel


typealias AuthorsDto = SirenModel<AuthorsDtoProperties>

data class AuthorsDtoProperties(val authors: List<isel.pdm.pdm2324i_gomoku_g37.domain.Author>)

val AuthorsDtoType = SirenModel.getType<AuthorsDtoProperties>()