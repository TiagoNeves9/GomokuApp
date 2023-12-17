package com.example.pdm2324i_gomoku_g37.service

import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.LobbyId
import com.example.pdm2324i_gomoku_g37.domain.ReadyLobby
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.WaitingLobby
import com.example.pdm2324i_gomoku_g37.service.utils.PathTemplate
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URL
import kotlin.coroutines.resumeWithException

private const val GOMOKU_API_URL = "http://10.0.2.2:8080"

class RealGomokuService(
    private val client: OkHttpClient,
    private val gson: Gson,
    private val baseRequestUrl: URL = URL(GOMOKU_API_URL),
) : GomokuService {

    private val authorsRequest by lazy {
        Request.Builder()
            .url(GOMOKU_API_URL + PathTemplate.AUTHORS)
            .addHeader("accept", "application/json")
            .build()
    }

    override suspend fun fetchAuthors(): List<Author> = suspendCancellableCoroutine { continuation ->
        val call = client.newCall(authorsRequest)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                continuation.resumeWithException(ApiErrorException("Could not authors joke", e))
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body
                if (!response.isSuccessful || body == null)
                    continuation.resumeWithException(
                        ApiErrorException("Could not fetch joke. Remote service returned ${response.code}")
                    )
                else
                    continuation.resumeWith(Result.success(gson.fromJson(body.string(), JokeDto::class.java).toJoke()))
            }

        }
    }

    override suspend fun fetchLobbies(): List<WaitingLobby> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchApiInfo(): String {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(username: String, password: String): UserInfo {
        TODO("Not yet implemented")
    }

    override suspend fun login(username: String, password: String): UserInfo {
        TODO("Not yet implemented")
    }

    override suspend fun fetchRankings(): GomokuRankings.Rankings {
        TODO("Not yet implemented")
    }

    override suspend fun createLobby(token: String, rules: Rules): Flow<WaitingLobby> {
        TODO("Not yet implemented")
    }

    override suspend fun lobbyInfo(token: String, lobbyId: String): WaitingLobby {
        TODO("Not yet implemented")
    }

    override suspend fun enterLobby(token: String, lobbyId: String): Flow<ReadyLobby> {
        TODO("Not yet implemented")
    }

    override suspend fun leaveLobby(token: String, lobbyId: String): LobbyId {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUser(token: String, userId: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun createGame(
        token: String,
        lobbyId: String,
        host: User,
        joined: User
    ): Game {
        TODO("Not yet implemented")
    }

}