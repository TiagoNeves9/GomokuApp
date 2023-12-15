package com.example.pdm2324i_gomoku_g37.service

import android.util.Log
import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.WaitingLobby
import com.example.pdm2324i_gomoku_g37.domain.LobbyId
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.ReadyLobby
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Turn
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.domain.board.BOARD_DIM
import com.example.pdm2324i_gomoku_g37.domain.board.Board
import com.example.pdm2324i_gomoku_g37.domain.board.createBoard
import com.example.pdm2324i_gomoku_g37.domain.toGameDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import java.time.Instant


private const val FAKE_SERVICE_DELAY = 1000L
private const val FAKE_SERVICE_APP_VERSION = "1.0.0"
private const val FAKE_SERVICE_API_INFO = "The Gomoku application is in version $FAKE_SERVICE_APP_VERSION and was made by Group 37 - Class 53D"
private const val FAKE_USER_TOKEN_LENGTH = 10

class FakeGomokuService : GomokuService {
    override suspend fun fetchAuthors(): List<Author> {
        delay(FAKE_SERVICE_DELAY)
        return GomokuAuthors.authors
    }

    override suspend fun fetchLobbies(): List<WaitingLobby> {
        delay(FAKE_SERVICE_DELAY)
        return GomokuLobbies.lobbies
    }

    override suspend fun fetchApiInfo(): String {
        delay(FAKE_SERVICE_DELAY)
        return FAKE_SERVICE_API_INFO
    }

    override suspend fun signUp(username: String, password: String): UserInfo {
        delay(FAKE_SERVICE_DELAY)
        return GomokuUsers.createUser(username, password) ?: throw UserAlreadyExists()
    }

    override suspend fun login(username: String, password: String): UserInfo {
        delay(FAKE_SERVICE_DELAY)
        val user = GomokuUsers.validateLogIn(username, password) ?: throw InvalidLogin()
        return GomokuUsers.createToken(user.id, username)
    }

    override suspend fun fetchRankings(): GomokuRankings.Rankings {
        delay(FAKE_SERVICE_DELAY)
        return GomokuRankings.rankings.first()
    }

    override suspend fun createLobby(token: String, rules: Rules): Flow<WaitingLobby> = callbackFlow {
        val user = GomokuUsers.getUserByToken(token) ?: throw InvalidLogin()
        LobbyId(GomokuLobbies.createLobby(user.id, rules))
    }

    override suspend fun lobbyInfo(token: String, lobbyId: String): WaitingLobby {
        GomokuUsers.getUserByToken(token) ?: throw InvalidLogin()

        return GomokuLobbies.lobbies.firstOrNull { lobby ->
            lobby.lobbyId == lobbyId
        } ?: throw UnknownLobby()
    }

    override suspend fun enterLobby(token: String, lobbyId: String): Flow<ReadyLobby> = flow {
        /*val user = GomokuUsers.getUserByToken(token) ?: throw InvalidLogin()

        val lobby = GomokuLobbies.lobbies.firstOrNull { lobby ->
            lobby.lobbyId == lobbyId && lobby.guestUserId == null
        } ?: throw UnknownLobby()

        GomokuLobbies.updateGuestUser(user.id, lobby)

        val game = GomokuGames.games.firstOrNull { game ->
            game.users.second.first.id == user.id
        } ?: throw UnknownLobby()*/
        val game = GomokuGames.games.first()
        emit(ReadyLobby(game))
    }

    override suspend fun leaveLobby(token: String, lobbyId: String): LobbyId {
        val user = GomokuUsers.getUserByToken(token) ?: throw InvalidLogin()

        val lobby = GomokuLobbies.lobbies.firstOrNull { lobby ->
            lobby.lobbyId == lobbyId && (lobby.guestUserId == user.id || lobby.hostUserId == user.id)
        } ?: throw UnknownLobby()

        return if (lobby.guestUserId == user.id) {
            LobbyId(GomokuLobbies.updateGuestUser(user.id, lobby).lobbyId)
        } else {
            //apagar lobby porque é o host
            GomokuLobbies.deleteLobby(lobby)
        }
    }

    override suspend fun fetchUser(token: String, userId: String): User {
        delay(FAKE_SERVICE_DELAY)
        GomokuUsers.getUserByToken(token) ?: throw InvalidLogin() //depende da api
        return GomokuUsers.users.firstOrNull { it.id == userId } ?: throw UnknownUser()
    }

    override suspend fun createGame(token: String, lobbyId: String, host: User, joined: User): Game {
        GomokuUsers.getUserByToken(token) ?: throw InvalidLogin()

        val hostPlayer = Player(host, Turn.BLACK_PIECE)
        val guestPlayer = Player(joined, Turn.WHITE_PIECE)

        val lobby = GomokuLobbies.lobbies.firstOrNull { lobby ->
            lobby.lobbyId == lobbyId && lobby.hostUserId == host.id && lobby.guestUserId != null
        } ?: throw UnknownLobby()

        val board = createBoard(boardSize = lobby.rules.boardDim)

        return GomokuGames.createGame(Pair(hostPlayer, guestPlayer), board, hostPlayer, 0, Instant.now(), lobby.rules)
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
    private val _lobbies: MutableList<WaitingLobby> = mutableListOf(
        WaitingLobby(
            "1",
            "1",
            null,
            Rules(15, Opening.FREESTYLE, Variant.FREESTYLE)
        ),
        WaitingLobby(
            "2",
            "2",
            null,
            Rules(19, Opening.FREESTYLE, Variant.FREESTYLE)
        ),
        WaitingLobby(
            "3",
            "3",
            null,
            Rules(15, Opening.PRO, Variant.FREESTYLE)
        ),
        WaitingLobby(
            "4",
            "4",
            null,
            Rules(19, Opening.PRO, Variant.FREESTYLE)
        ),
        WaitingLobby(
            "5",
            "5",
            null,
            Rules(15, Opening.FREESTYLE, Variant.SWAP_AFTER_FIRST)
        ),
        WaitingLobby(
            "6",
            "6",
            null,
            Rules(19, Opening.FREESTYLE, Variant.SWAP_AFTER_FIRST)
        ),
    )

    val lobbies: List<WaitingLobby>
        get() = _lobbies.toList()

    fun createLobby(userId: String, rules: Rules): String {
        val lobbyId = generateRandomString()
        val waitingLobby = WaitingLobby(lobbyId, userId, null, rules)
        _lobbies.add(waitingLobby)
        return lobbyId
    }

    fun updateGuestUser(userId: String?, lobby: WaitingLobby): WaitingLobby {
        val newLobby = WaitingLobby(lobby.lobbyId, lobby.hostUserId, userId, lobby.rules)
        _lobbies.remove(lobby)
        _lobbies.add(newLobby)
        return newLobby
    }

    fun deleteLobby(lobby: WaitingLobby): LobbyId {
        val lobbyId = LobbyId(lobby.lobbyId)
        _lobbies.remove(lobby)
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

    private val _tokens: MutableMap<String, String> = mutableMapOf()

    val tokens: Map<String, String>
        get() = _tokens.toMap()

    fun createToken(userId: String, username: String): UserInfo {
        val token = generateRandomString()
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
        val userId: String = generateRandomString()
        val token = generateRandomString()
        _users.add(User(userId, username))
        _passwords[userId] = password
        _tokens[userId] = token
        return UserInfo(userId, username, token)
    }
}

object GomokuGames {
    private val _games: MutableList<Game> = mutableListOf(
        Game(
            gameId = "1",
            users = Pair(Player(User("2", "jp"), Turn.BLACK_PIECE), Player(User("1", "tbmaster"), Turn.WHITE_PIECE)),
            board = createBoard(boardSize = 19),
            currentPlayer = Player(User("2", "jp"), Turn.BLACK_PIECE),
            0,
            now = Instant.now(),
            rules = Rules(19, Opening.FREESTYLE, Variant.FREESTYLE)
        )
    )

    val games: List<Game>
        get() = _games.toList()

    fun createGame(
        users: Pair<Player, Player>,
        board: Board,
        currentPlayer: Player,
        score: Int,
        now: Instant,
        rules: Rules
    ): Game {
        val gameId = generateRandomString()
        return Game(gameId, users, board, currentPlayer, score, now, rules)
    }
}

private fun generateRandomString(): String =
    (1..FAKE_USER_TOKEN_LENGTH)
        .map { ('a'..'z') + ('A'..'Z') + ('0'..'9').random() }
        .joinToString("")