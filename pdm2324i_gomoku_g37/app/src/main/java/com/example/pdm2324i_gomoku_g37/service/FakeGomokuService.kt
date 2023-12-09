package com.example.pdm2324i_gomoku_g37.service

import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.Lobby
import com.example.pdm2324i_gomoku_g37.domain.LobbyId
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.Variant
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


private const val FAKE_SERVICE_DELAY = 1000L
private const val FAKE_SERVICE_APP_VERSION = "1.0.0"
private const val FAKE_SERVICE_API_INFO = "The Gomoku application is in version $FAKE_SERVICE_APP_VERSION and was made by Group 37 - Class 53D"
private const val FAKE_USER_TOKEN_LENGTH = 10

class FakeGomokuService : GomokuService {
    override suspend fun fetchAuthors(): List<Author> {
        delay(FAKE_SERVICE_DELAY)
        return GomokuAuthors.authors
    }

    override suspend fun fetchLobbies(): List<Lobby> {
        delay(FAKE_SERVICE_DELAY)
        return GomokuLobbies.lobbies
    }

    override suspend fun fetchApiInfo(): String {
        delay(FAKE_SERVICE_DELAY)
        return FAKE_SERVICE_API_INFO
    }

    override suspend fun signUp(username: String, password: String): UserInfo {
        delay(FAKE_SERVICE_DELAY)
        return GomokuUsers.createUser(username, password) ?: throw FetchGomokuException("Username already exists")
    }

    override suspend fun login(username: String, password: String): UserInfo {
        delay(FAKE_SERVICE_DELAY)
        val user = GomokuUsers.validateLogIn(username, password) ?: throw FetchGomokuException("Incorrect login information")
        return GomokuUsers.createToken(user.id, username)
    }

    override suspend fun fetchRankings(): GomokuRankings.Rankings {
        delay(FAKE_SERVICE_DELAY)
        return GomokuRankings.rankings.first()
    }

    override suspend fun createLobby(token: String, rules: Rules): Flow<Lobby> = callbackFlow {
        val user = GomokuUsers.getUserByToken(token) ?: throw FetchGomokuException("Authentication required")
        LobbyId(GomokuLobbies.createLobby(user.id, rules))
    }

    override suspend fun lobbyInfo(token: String, lobbyId: String): Lobby {
        val user = GomokuUsers.getUserByToken(token) ?: throw FetchGomokuException("Authentication required")

        return GomokuLobbies.lobbies.firstOrNull { lobby ->
            lobby.lobbyId == lobbyId  && lobby.hostUserId == user.id
        } ?: throw FetchGomokuException("Lobby not found")
    }

    override suspend fun enterLobby(token: String, lobbyId: String): Game {
        TODO("Not yet implemented")
    }

    override suspend fun leaveLobby(token: String, lobbyId: String): LobbyId {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUser(token: String, userId: String): User {
        delay(FAKE_SERVICE_DELAY)
        GomokuUsers.getUserByToken(token) ?: throw FetchGomokuException("Authentication required") //depende da api
        return GomokuUsers.users.firstOrNull { it.id == userId } ?: throw FetchGomokuException("User not found")
    }

    override suspend fun createGame(token: String, lobbyId: String, host: User, joined: User): Game {
        TODO("Not yet implemented")
    }
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
        get() = _rankings.toList()
}

object GomokuUsers {
    private val _users: MutableList<User> = mutableListOf(
        User("1", "tbmaster"),
        User("2", "jp"),
        User("3", "noobmaster69")
    )

    val users: List<User>
        get() = _users.toList()

    private val _passwords: MutableMap<String, String> = mutableMapOf(
        "1" to "jubas",
        "2" to "paulinho",
        "3" to "qwerty"
    )

    val passwords: Map<String, String>
        get() = _passwords.toMap()

    fun validateLogIn(username: String, password: String): User? =
        _users.firstOrNull { user ->
            user.username == username && _passwords[user.id] == password
        }

    private fun generateRandomToken(): String =
        (1..FAKE_USER_TOKEN_LENGTH)
            .map { ('a'..'z') + ('A'..'Z') + ('0'..'9').random() }
            .joinToString("")

    private val _tokens: MutableMap<String, String> = mutableMapOf(
        "1" to "123",
        "2" to "456",
        "3" to "789"
    )

    val tokens: Map<String, String>
        get() = _tokens.toMap()

    fun createToken(userId: String, username: String): UserInfo {
        val token = generateRandomToken()
        _tokens[userId] = token
        return UserInfo(userId, username, token)
    }

    fun getUserByToken(token: String): User? {
        val id: String? = _tokens.entries.firstOrNull { (_, v) ->
            v == token
        }?.key
        return _users.firstOrNull { it.id == id }
    }

    fun createUser(username: String, password: String): UserInfo? {
        if (users.firstOrNull { it.username == username } != null) return null
        val userId: String = (users.size + 1).toString()
        val token = generateRandomToken()
        _users.add(User(userId, username))
        _passwords[userId] = password
        _tokens[userId] = token
        return UserInfo(userId, username, token)
    }
}