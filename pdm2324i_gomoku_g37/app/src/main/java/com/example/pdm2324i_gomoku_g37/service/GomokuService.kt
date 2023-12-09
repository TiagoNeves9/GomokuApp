package com.example.pdm2324i_gomoku_g37.service

import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.Lobby
import com.example.pdm2324i_gomoku_g37.domain.LobbyId
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Token
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import kotlinx.coroutines.flow.Flow

interface GomokuService {
    /**
    * Fetches the authors from the service
    */
    suspend fun fetchAuthors(): List<Author>

    suspend fun fetchLobbies(): List<Lobby>

    suspend fun fetchApiInfo(): String

    suspend fun signUp(username: String, password: String): UserInfo

    suspend fun login(username: String, password: String): UserInfo

    suspend fun fetchRankings() : GomokuRankings.Rankings

    suspend fun createLobby(token: String, rules: Rules): Flow<Lobby>

    suspend fun lobbyInfo(token: String, lobbyId: String): Lobby

    suspend fun enterLobby(token: String, lobbyId: String): Game

    suspend fun leaveLobby(token: String, lobbyId: String): LobbyId

    suspend fun fetchUser(token: String, userId: String): User

    suspend fun createGame(token: String, lobbyId: String, host: User, joined: User): Game
}

/**
 * Represents an error that occurred while fetching a joke.
 * @param message The error message
 * @param cause The cause of the error, if any
 */
class FetchGomokuException(message: String, cause: Throwable? = null)
    : Exception(message, cause)