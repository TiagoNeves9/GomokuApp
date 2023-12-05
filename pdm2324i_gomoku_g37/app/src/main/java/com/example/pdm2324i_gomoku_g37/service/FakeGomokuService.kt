package com.example.pdm2324i_gomoku_g37.service

import android.util.Log
import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.Lobby
import com.example.pdm2324i_gomoku_g37.domain.LobbyId
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Token
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserId
import com.example.pdm2324i_gomoku_g37.domain.Variant
import kotlinx.coroutines.delay


private const val FAKE_SERVICE_DELAY = 1000L
private const val FAKE_SERVICE_APP_VERSION = "X.X.X"

class FakeGomokuService : GomokuService {
    override suspend fun fetchAuthors(): List<Author> {
        delay(FAKE_SERVICE_DELAY)
        return GomokuAuthors.authors
    }

    override suspend fun fetchLobbies(): List<Lobby> {
        delay(FAKE_SERVICE_DELAY)
        return GomokuLobbies.lobbies
    }

    override suspend fun fetchProfile(): String {
        delay(FAKE_SERVICE_DELAY)
        return "CHANGE THIS" //TODO
    }

    override suspend fun fetchInfo(): String {
        delay(FAKE_SERVICE_DELAY)
        return "The Gomoku application is in version $FAKE_SERVICE_APP_VERSION" +
                " and was made by Group 37 - Class 53D"
    }

    override suspend fun signUp(username: String, password: String): UserId {
        delay(FAKE_SERVICE_DELAY)
        return UserId(GomokuUsers.createUser(username, password))
    }

    override suspend fun signIn(username: String, password: String): Token {
        delay(FAKE_SERVICE_DELAY)
        val user = GomokuUsers.users.firstOrNull { user ->
            user.username == username && user.encodedPassword == password
        }
        if (user != null) {
            val token = Token(generateRandomString(10))
            GomokuUsers.createToken(user.id, token.token)
            return token
        }
        else throw Exception("User Not Found")
    }

    override suspend fun fetchRankings(): GomokuRankings.Rankings = GomokuRankings.rankings.first()

    override suspend fun logIn(username: String, password: String): User {
        delay(FAKE_SERVICE_DELAY)
        val user = GomokuUsers.users.firstOrNull { user ->
            user.username == username && user.encodedPassword == password
        }
        return user ?: throw Exception("User Not Found")
    }

    override suspend fun createLobby(token: String, rules: Rules): LobbyId {
        val id: String? = GomokuUsers.tokens.entries.firstOrNull { (_, t) ->
            t == token
        }?.key
        return if (id != null) LobbyId(GomokuLobbies.createLobby(id, rules))
        else throw Exception("User Not Found")
    }

    override suspend fun lobbyInfo(token: String, lobbyId: String): Lobby {
        val id: String? = GomokuUsers.tokens.entries.firstOrNull { (_, t) ->
            t == token
        }?.key

        val lobby: Lobby? = GomokuLobbies.lobbies.firstOrNull { lobby ->
            lobby.lobbyId == lobbyId
        }

        if (id == null) throw Exception("Unauthenticated")

        if (lobby == null) throw Exception("Lobby not found")

        return lobby
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
    private val _lobbies: MutableList<Lobby> = mutableListOf(
        Lobby(
            "1",
            "1",
            null,
            Rules(15, Opening.FREESTYLE, Variant.FREESTYLE)
        ),
        Lobby(
            "2",
            "2",
            null,
            Rules(19, Opening.FREESTYLE, Variant.FREESTYLE)
        ),
        Lobby(
            "3",
            "3",
            null,
            Rules(15, Opening.PRO, Variant.FREESTYLE)
        ),
        Lobby(
            "4",
            "4",
            null,
            Rules(19, Opening.PRO, Variant.FREESTYLE)
        ),
        Lobby(
            "5",
            "5",
            null,
            Rules(15, Opening.FREESTYLE, Variant.SWAP_AFTER_FIRST)
        ),
        Lobby(
            "6",
            "6",
            null,
            Rules(19, Opening.FREESTYLE, Variant.SWAP_AFTER_FIRST)
        ),
    )

    val lobbies: List<Lobby>
        get() = _lobbies.toList()

    fun createLobby(userId: String, rules: Rules): String {
        val lobbyId = (_lobbies.size + 1).toString()
        val lobby = Lobby(lobbyId, userId, null, rules)
        _lobbies.add(lobby)
        return lobbyId
    }
}

object GomokuRankings {
    data class Rankings(val user: String, val nGames: Int, val score: Int)

    private val _rankings: MutableList<Rankings> = mutableListOf(
        Rankings("admin", 9, 666)
    )

    val rankings: List<Rankings>
        get() = _rankings
}

object GomokuUsers {
    private val _users: MutableList<User> = mutableListOf(
        User("1", "tbmaster", "jubas"),
        User("2", "jp", "paulinho"),
        User("3", "noobmaster69", "qwerty")
    )

    val users: List<User>
        get() = _users.toList()

    private val _tokens: MutableMap<String, String> = mutableMapOf(
        "1" to "123",
        "2" to "456",
        "3" to "789"
    )

    val tokens: Map<String, String>
        get() = _tokens.toMap()

    fun createToken(userId: String, token: String) {
        _tokens[userId] = token
    }

    fun createUser(username: String, password: String): String {
        val user = users.firstOrNull { user -> user.username == username }
        if (user != null) throw IllegalArgumentException("User already exists")
        val id: String = (users.size + 1).toString()
        val newUser = User(id, username, password)
        _users.add(newUser)
        return id
    }
}