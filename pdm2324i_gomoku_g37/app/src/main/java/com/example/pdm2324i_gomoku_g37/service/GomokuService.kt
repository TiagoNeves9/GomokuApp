package com.example.pdm2324i_gomoku_g37.service

import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.Lobby
import com.example.pdm2324i_gomoku_g37.domain.Token
import com.example.pdm2324i_gomoku_g37.domain.UserId

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
}

/**
 * Represents an error that occurred while fetching a joke.
 * @param message The error message
 * @param cause The cause of the error, if any
 */
class FetchGomokuException(message: String, cause: Throwable? = null)
    : Exception(message, cause)