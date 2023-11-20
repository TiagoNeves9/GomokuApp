package com.example.pdm2324i_gomoku_g37.service

import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.Token
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserId
import kotlinx.coroutines.delay

private const val FAKE_SERVICE_DELAY = 3000L

class FakeGomokuService : GomokuService {
    override suspend fun fetchAuthors(): List<Author> = GomokuAuthors.authors

    override suspend fun signUp(username: String, password: String): UserId {
        delay(FAKE_SERVICE_DELAY)
        return UserId(GomokuUsers.createUser(username, password))
    }

    override suspend fun fetchInfo(): String = "This is Gomoku Version X.X.X made by G37-53D"

    override suspend fun signIn(username: String, password: String): Token {
        delay(FAKE_SERVICE_DELAY)
        val user = GomokuUsers.users.firstOrNull { user ->
            user.username == username && user.encodedPassword == password
        }
        if (user != null) return Token(generateRandomString(10))
        else throw Exception("User Not Found")
    }
}

private fun generateRandomString(length: Int): String {
    val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { charset.random() }
        .joinToString("")
}

object GomokuAuthors {
    val authors: List<Author> = listOf(
        Author(
            48292,
            "Tiago Neves",
            "O melhor programador",
            "img_tiago",
            "a48292@alunos.isel.pt"
        ),
        Author(
            48333,
            "Tomás Barroso",
            "O programador mediano",
            "img_tomas",
            "a48333@alunos.isel.pt"
        ),
        Author(
            48264,
            "João Pereira",
            "O pior programador",
            "img_joao",
            "a48264@alunos.isel.pt"
        )
    )
}

object GomokuUsers {
    private val _users: MutableList<User> = mutableListOf(
        User(1, "tbmaster", "jubas"),
        User(2, "jp", "paulinho"),
        User(3, "noobmaster69", "qwerty")
    )

    val users: List<User>
        get() = _users.toList()

    private val _tokens: MutableMap<Int, String> = mutableMapOf(
        1 to "123",
        2 to "456",
        3 to "789"
    )

    val tokens: Map<Int, String>
        get() = _tokens

    fun createUser(username: String, password: String): Int {
        val newUser = User(users.size + 1, username, password)
        _users.add(newUser)
        return newUser.id
    }
}