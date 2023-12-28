package com.example.pdm2324i_gomoku_g37.domain.dtos

import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.service.utils.SirenModel

typealias AuthorsDto = SirenModel<AuthorsDtoProperties>

data class AuthorsDtoProperties(val authors: List<Author>)

val AuthorsDtoType = SirenModel.getType<AuthorsDtoProperties>()





