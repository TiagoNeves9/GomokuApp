package com.example.pdm2324i_gomoku_g37.service

import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.UserId

interface GomokuService {
    /*
    * Fetches the authors from the service
    */
    suspend fun fetchAuthors(): List<Author>

    suspend fun signUp(username: String, password: String): UserId
}