package com.example.pdm2324i_gomoku_g37.service

import com.example.pdm2324i_gomoku_g37.domain.Author

class FakeJokesService : GomokuService {

    private val authors: List<Author> = listOf(
        Author(48292, "Tiago Neves", "O melhor programador", "img_tiago"),
        Author(48333, "Tomás Barroso", "O programador mediano", "img_tomas"),
        Author(48264, "João Pereira", "O pior programador", "img_joao"),
    )

    override suspend fun fetchAuthors(): List<Author> = authors
}