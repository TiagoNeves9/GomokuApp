package isel.pdm.pdm2324i_gomoku_g37.service

import isel.pdm.pdm2324i_gomoku_g37.domain.Author
import isel.pdm.pdm2324i_gomoku_g37.domain.Game
import isel.pdm.pdm2324i_gomoku_g37.domain.WaitingLobby
import isel.pdm.pdm2324i_gomoku_g37.domain.ReadyLobby
import isel.pdm.pdm2324i_gomoku_g37.domain.Rules
import isel.pdm.pdm2324i_gomoku_g37.domain.User
import isel.pdm.pdm2324i_gomoku_g37.domain.UserInfo
import isel.pdm.pdm2324i_gomoku_g37.domain.UserStatistics
import isel.pdm.pdm2324i_gomoku_g37.domain.board.Cell
import isel.pdm.pdm2324i_gomoku_g37.service.utils.ProblemJson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType


interface GomokuService {
    /**
     * Fetches the authors from the service
     */
    suspend fun fetchAuthors(): List<Author>

    suspend fun fetchLobbies(): List<WaitingLobby>

    suspend fun fetchApiInfo(): String

    suspend fun signUp(username: String, password: String): UserInfo

    suspend fun login(username: String, password: String): UserInfo

    suspend fun fetchRankings(): List<UserStatistics>

    suspend fun createLobby(token: String, rules: Rules): Flow<WaitingLobby>

    suspend fun joinLobby(token: String, lobby: WaitingLobby): Flow<ReadyLobby>

    suspend fun leaveLobby(token: String, lobbyId: String): String

    suspend fun fetchUserAccount(userId: String): User

    suspend fun isGameCreated(token: String, lobbyId: String): String

    suspend fun getGameById(token: String, gameId: String): Game

    suspend fun play(token: String, gameId: String, cell: Cell, boardSize: Int): Game

    suspend fun userRanking(username: String): UserStatistics
}

/**
 * Represents an error that occurred while fetching a joke.
 * @param message The error message
 * @param cause The cause of the error, if any
 */
abstract class FetchGomokuException(message: String, cause: Throwable? = null) :
    Exception(message, cause)

class UserAlreadyExists : FetchGomokuException("user already exists")

class InvalidLogin : FetchGomokuException("invalid credentials")

class UnknownLobby : FetchGomokuException("Lobby not found")

class UnknownUser : FetchGomokuException("User not found")

class FetchGomokuError(message: String, cause: Throwable?) :
    FetchGomokuException(message, cause)

class UnexpectedResponseException(
    contentType: MediaType? = null,
) : FetchGomokuException(
    message = "Unexpected content type [$contentType] response from the API."
)

class ApiUnauthorizedException() : FetchGomokuException("Unauthorized Access")

class ApiErrorException(
    val problemJson: ProblemJson
) : FetchGomokuException(message = problemJson.detail ?: "Something went wrong")