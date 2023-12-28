package com.example.pdm2324i_gomoku_g37.service

import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.WaitingLobby
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.ReadyLobby
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Turn
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.UserStatistics
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.domain.board.BOARD_DIM
import com.example.pdm2324i_gomoku_g37.domain.board.Board
import com.example.pdm2324i_gomoku_g37.domain.board.Cell
import com.example.pdm2324i_gomoku_g37.domain.board.createBoard
import com.example.pdm2324i_gomoku_g37.domain.toOpening
import com.example.pdm2324i_gomoku_g37.domain.toOpeningString
import com.example.pdm2324i_gomoku_g37.domain.toVariant
import com.example.pdm2324i_gomoku_g37.domain.toVariantString
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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
        return GomokuUsers.createToken(user.userId, username)
    }

    override suspend fun fetchRankings(): List<UserStatistics> {
        delay(FAKE_SERVICE_DELAY)
        return GomokuRankings.rankings
    }

    override suspend fun createLobby(token: String, rules: Rules): Flow<WaitingLobby> = flow {
        val user = GomokuUsers.getUserByToken(token) ?: throw InvalidLogin()
        val lobbyId = GomokuLobbies.createLobby(user.userId, rules)
        val lobby = WaitingLobby(
            lobbyId, user.userId, rules.boardDim,
            rules.opening.toOpeningString(), rules.variant.toVariantString()
        )
        emit(lobby)
    }

    override suspend fun joinLobby(token: String, lobby: WaitingLobby): Flow<ReadyLobby> = flow {
        val user = GomokuUsers.getUserByToken(token) ?: throw InvalidLogin()

        val hostUser = GomokuUsers.users.firstOrNull { it.userId == lobby.hostUserId } ?: throw UnknownUser()

        val game = GomokuGames.createGame(
            users = Pair(hostUser, user),
            board = createBoard(Turn.BLACK_PIECE, lobby.boardDim),
            currentPlayer = Player(hostUser, Turn.BLACK_PIECE),
            score = 0,
            now = Instant.now(),
            rules = Rules(lobby.boardDim, lobby.opening.toOpening(), lobby.variant.toVariant())
        )

        GomokuLobbies.deleteLobby(lobby)

        emit(ReadyLobby(game))
    }

    override suspend fun leaveLobby(token: String, lobbyId: String): String {
        val user = GomokuUsers.getUserByToken(token) ?: throw InvalidLogin()

        val lobby = GomokuLobbies.lobbies.firstOrNull { lobby ->
            lobby.lobbyId == lobbyId
        } ?: throw UnknownLobby()

        if (lobby.hostUserId == user.userId) {
            GomokuLobbies.deleteLobby(lobby)
        }

        return "Left Lobby"
    }

    override suspend fun fetchUserAccount(userId: String): User {
        delay(FAKE_SERVICE_DELAY)
        return GomokuUsers.users.firstOrNull { it.userId == userId } ?: throw UnknownUser()
    }

    override suspend fun isGameCreated(token: String, lobbyId: String): String {
        delay(FAKE_SERVICE_DELAY)
        val game = GomokuGames.games.firstOrNull { it.gameId == lobbyId }
        return if (game != null) "CREATED" else "WAITING"
    }

    override suspend fun getGameById(token: String, gameId: String): Game {
        delay(FAKE_SERVICE_DELAY)
        val user = GomokuUsers.getUserByToken(token) ?: throw InvalidLogin()
        return GomokuGames.games.firstOrNull {
            it.gameId == gameId && (it.users.first == user || it.users.second == user)
        } ?: throw GameNotFound()
    }

    override suspend fun play(token: String, gameId: String, cell: Cell, boardSize: Int): Game {
        delay(FAKE_SERVICE_DELAY)
        val user = GomokuUsers.getUserByToken(token) ?: throw InvalidLogin()
        val game = GomokuGames.games.firstOrNull {
            it.gameId == gameId && (it.users.first == user || it.users.second == user)
        } ?: throw GameNotFound()
        return game.computeNewGame(cell)
    }

    override suspend fun userRanking(username: String): UserStatistics {
        delay(FAKE_SERVICE_DELAY)
        return GomokuRankings.rankings.firstOrNull { it.user == username } ?: throw UnknownUser()
    }
}

object GomokuAuthors {
    val authors: List<Author> = listOf(
        Author(
            48292,
            "Tiago Neves",
            "a48292@alunos.isel.pt"
        ),
        Author(
            48333,
            "Tomás Barroso",
            "a48333@alunos.isel.pt"
        ),
        Author(
            48264,
            "João Pereira",
            "a48264@alunos.isel.pt"
        )
    )
}

object GomokuLobbies {
    private val _lobbies: MutableList<WaitingLobby> = mutableListOf(
        WaitingLobby(
            "1",
            "4",
            15,
            Opening.FREESTYLE.toOpeningString(),
            Variant.FREESTYLE.toVariantString()
        ),
        WaitingLobby(
            "2",
            "2",
            19,
            Opening.FREESTYLE.toOpeningString(),
            Variant.FREESTYLE.toVariantString()
        ),
        WaitingLobby(
            "3",
            "6",
            15,
            Opening.PRO.toOpeningString(),
            Variant.FREESTYLE.toVariantString()
        ),
        WaitingLobby(
            "4",
            "7",
            19,
            Opening.PRO.toOpeningString(),
            Variant.FREESTYLE.toVariantString()
        ),
        WaitingLobby(
            "5",
            "8",
            15,
            Opening.FREESTYLE.toOpeningString(),
            Variant.SWAP_AFTER_FIRST.toVariantString()
        ),
        WaitingLobby(
            "6",
            "9",
            19,
            Opening.FREESTYLE.toOpeningString(),
            Variant.SWAP_AFTER_FIRST.toVariantString()
        ),
    )

    val lobbies: List<WaitingLobby>
        get() = _lobbies.toList()

    fun createLobby(userId: String, rules: Rules): String {
        val lobbyId = generateRandomString()
        val waitingLobby = WaitingLobby(lobbyId, userId, rules.boardDim, rules.opening.toOpeningString(), rules.variant.toVariantString())
        _lobbies.add(waitingLobby)
        return lobbyId
    }

    fun deleteLobby(lobby: WaitingLobby) {
        _lobbies.remove(lobby)
    }
}

object GomokuRankings {

    private val _rankings: MutableList<UserStatistics> = mutableListOf(
        UserStatistics("tbmaster", 9, 666),
        UserStatistics("jp", 1234, 32),
        UserStatistics("noobmaster69", 4321, 11)
    )

    val rankings: List<UserStatistics>
        get() = _rankings.toList()
}

object GomokuUsers {
    private val _users: MutableList<User> = mutableListOf(
        User("1", "tbmaster", "jubas"),
        User("2", "jp", "paulinho"),
        User("3", "noobmaster69", "qwerty")
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
            user.username == username && _passwords[user.userId] == password
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
        return _users.firstOrNull { it.userId == id }
    }

    fun createUser(username: String, password: String): UserInfo? {
        if (users.firstOrNull { it.username == username } != null) return null
        val userId: String = generateRandomString()
        val token = generateRandomString()
        _users.add(User(userId, username, password))
        _passwords[userId] = password
        _tokens[userId] = token
        return UserInfo(userId, username, token)
    }
}

object GomokuGames {
    private val _games: MutableList<Game> = mutableListOf(
        Game(
            gameId = "1",
            users = Pair(User("2", "jp", "paulinho"), User("1", "tbmaster", "jubas")),
            board = createBoard(boardSize = BOARD_DIM),
            currentPlayer = Player(User("2", "jp", "paulinho"), Turn.BLACK_PIECE),
            rules = Rules(BOARD_DIM, Opening.FREESTYLE, Variant.FREESTYLE)
        )
    )

    val games: List<Game>
        get() = _games.toList()

    fun createGame(
        users: Pair<User, User>,
        board: Board,
        currentPlayer: Player,
        score: Int,
        now: Instant,
        rules: Rules
    ): Game {
        val gameId = generateRandomString()
        return Game(gameId, users, board, currentPlayer, rules)
    }
}

private fun generateRandomString(): String =
    (1..FAKE_USER_TOKEN_LENGTH)
        .map { ('a'..'z') + ('A'..'Z') + ('0'..'9').random() }
        .joinToString("")