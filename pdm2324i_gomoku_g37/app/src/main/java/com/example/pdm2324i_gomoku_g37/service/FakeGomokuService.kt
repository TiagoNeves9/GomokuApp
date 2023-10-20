package com.example.pdm2324i_gomoku_g37.service

import com.example.pdm2324i_gomoku_g37.domain.Author

class FakeGomokuService : GomokuService {

    private val authors: List<Author> = listOf(
        Author(48292, "Tiago Neves", "O melhor programador", "img_tiago", "A48292@alunos.isel.pt"),
        Author(48333, "Tomás Barroso", "O programador mediano", "img_tomas", "A48333@alunos.isel.pt"),
        Author(48264, "João Pereira", "O pior programador", "img_joao", "A48264@alunos.isel.pt"),
    )



    override suspend fun fetchAuthors(): List<Author> = authors
}