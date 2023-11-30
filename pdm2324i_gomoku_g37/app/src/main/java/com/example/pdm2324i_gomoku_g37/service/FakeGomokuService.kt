package com.example.pdm2324i_gomoku_g37.service

import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.Lobby
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Token
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserId
import com.example.pdm2324i_gomoku_g37.domain.Variant
import kotlinx.coroutines.delay
import java.util.UUID

private const val FAKE_SERVICE_DELAY = 1000L

class FakeGomokuService : GomokuService {
    override suspend fun fetchAuthors(): List<Author> {
        delay(FAKE_SERVICE_DELAY)
        return GomokuAuthors.authors
    }

    override suspend fun fetchLobbies(): List<Lobby> = GomokuLobbies.lobbies

    override suspend fun fetchProfile(): String = "CHANGE THIS" //TODO

    override suspend fun fetchInfo(): String = "This is Gomoku Version X.X.X made by G37-53D"

    override suspend fun signUp(username: String, password: String): UserId {
        delay(FAKE_SERVICE_DELAY)
        return UserId(GomokuUsers.createUser(username, password))
    }

    override suspend fun signIn(username: String, password: String): Token {
        delay(FAKE_SERVICE_DELAY)
        val user = GomokuUsers.users.firstOrNull { user ->
            user.username == username && user.encodedPassword == password
        }
        if (user != null) return Token(generateRandomString(10))
        else throw Exception("User Not Found")
    }

    override suspend fun fetchRankings() : GomokuRankings.Rankings = GomokuRankings.rankings.first()

    override suspend fun logIn(username: String,password: String) : User {
        delay(FAKE_SERVICE_DELAY)
        val user = GomokuUsers.users.firstOrNull { user ->
            user.username == username && user.encodedPassword == password
        }

        if (user != null ) return  user
        else throw  Exception("User Not Found")
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

object GomokuLobbies {
    val lobbies: List<Lobby> = listOf(
        Lobby(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Rules(15, Opening.FREESTYLE, Variant.FREESTYLE)
        ),
        Lobby(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Rules(19, Opening.FREESTYLE, Variant.FREESTYLE)
        ),
        Lobby(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Rules(15, Opening.PRO, Variant.FREESTYLE)
        ),
        Lobby(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Rules(19, Opening.PRO, Variant.FREESTYLE)
        ),
        Lobby(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Rules(15, Opening.FREESTYLE, Variant.SWAP_AFTER_FIRST)
        ),
        Lobby(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Rules(19, Opening.FREESTYLE, Variant.SWAP_AFTER_FIRST)
        ),
    )
}

object GomokuRankings{
    data class Rankings(val user: String, val nGames: Int, val score: Int)

    private val _rankings: MutableList<Rankings> = mutableListOf(
        Rankings("admin",9,666)
    )

    val rankings : List<Rankings>
        get() = _rankings
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
        val user = GomokuUsers.users.firstOrNull { user -> user.username == username }
        if (user != null) throw IllegalArgumentException("User already exists")
        val newUser = User(users.size + 1, username, password)
        _users.add(newUser)
        return newUser.id
    }
}