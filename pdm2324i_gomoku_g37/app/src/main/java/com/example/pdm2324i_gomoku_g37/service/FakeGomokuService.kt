package com.example.pdm2324i_gomoku_g37.service

import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserId

class FakeGomokuService : GomokuService {
    override suspend fun fetchAuthors(): List<Author> = GomokuAuthors.authors
    override suspend fun signUp(username: String, password: String): UserId =
        UserId(GomokuUsers.addUser(username, password))
}

object GomokuAuthors {
    val authors: List<Author> = listOf(
        Author(48292, "Tiago Neves", "O melhor programador", "img_tiago", "A48292@alunos.isel.pt"),
        Author(48333, "Tomás Barroso", "O programador mediano", "img_tomas", "A48333@alunos.isel.pt"),
        Author(48264, "João Pereira", "O pior programador", "img_joao", "A48264@alunos.isel.pt"),
    )
}

object GomokuUsers {
    private val _users: MutableList<User> = mutableListOf(
        User(1, "tbmaster", "jubas"),
        User(2, "jp", "paulinho"),
        User(3, "noobmaster69", "qwerty"),
    )

    val users: List<User>
        get() = _users.toList()

    fun addUser(username: String, password: String): Int {
        val newUser = User(users.size + 1, username, password)
        _users.add(newUser)
        return newUser.id
    }
}