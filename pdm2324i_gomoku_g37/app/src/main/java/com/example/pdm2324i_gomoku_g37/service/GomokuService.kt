package com.example.pdm2324i_gomoku_g37.service

import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.Lobby
import com.example.pdm2324i_gomoku_g37.domain.LobbyId
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Token
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserId
import com.example.pdm2324i_gomoku_g37.domain.WaitingLobby
import kotlinx.coroutines.flow.Flow

interface GomokuService {
    /**
    * Fetches the authors from the service
    */
    suspend fun fetchAuthors(): List<Author>

    suspend fun fetchLobbies(): List<Lobby>

    suspend fun fetchProfile(): String

    suspend fun fetchInfo(): String

    suspend fun signUp(username: String, password: String): UserId

    suspend fun signIn(username: String, password: String): Token

    suspend fun fetchRankings() : GomokuRankings.Rankings

    suspend fun logIn(username: String, password: String): User

    suspend fun createLobby(token: String, rules: Rules): LobbyId

    suspend fun lobbyInfo(token: String, lobbyId: String): Lobby

    suspend fun enterLobby(token: String, lobbyId: String): Flow<WaitingLobby>

    suspend fun leaveLobby(token: String, lobbyId: String): LobbyId

    suspend fun userInfo(token: String, userId: String): User

    suspend fun createGame(token: String, lobbyId: String, host: User, joined: User): Game
}

/**
 * Represents an error that occurred while fetching a joke.
 * @param message The error message
 * @param cause The cause of the error, if any
 */
class FetchGomokuException(message: String, cause: Throwable? = null)
    : Exception(message, cause)