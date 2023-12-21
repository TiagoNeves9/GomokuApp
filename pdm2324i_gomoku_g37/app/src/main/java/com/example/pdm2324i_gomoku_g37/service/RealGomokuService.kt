package com.example.pdm2324i_gomoku_g37.service

import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.GameInfo
import com.example.pdm2324i_gomoku_g37.domain.LobbyId
import com.example.pdm2324i_gomoku_g37.domain.ReadyLobby
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.UserStatistics
import com.example.pdm2324i_gomoku_g37.domain.WaitingLobby
import com.example.pdm2324i_gomoku_g37.domain.dtos.AboutDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.AboutDtoType
import com.example.pdm2324i_gomoku_g37.domain.dtos.AuthorsDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.AuthorsDtoType
import com.example.pdm2324i_gomoku_g37.domain.dtos.GameDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.GameDtoType
import com.example.pdm2324i_gomoku_g37.domain.dtos.IsGameCreatedDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.IsGameCreatedDtoType
import com.example.pdm2324i_gomoku_g37.domain.dtos.LeaveLobbyDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.LeaveLobbyDtoType
import com.example.pdm2324i_gomoku_g37.domain.dtos.LobbiesDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.LobbiesDtoType
import com.example.pdm2324i_gomoku_g37.domain.dtos.LobbyDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.LobbyDtoType
import com.example.pdm2324i_gomoku_g37.domain.dtos.RankingsDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.RankingsDtoType
import com.example.pdm2324i_gomoku_g37.domain.dtos.UserInfoDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.UserInfoDtoType
import com.example.pdm2324i_gomoku_g37.domain.dtos.toGame
import com.example.pdm2324i_gomoku_g37.domain.dtos.toUserInfo
import com.example.pdm2324i_gomoku_g37.domain.dtos.toWaitingLobby
import com.example.pdm2324i_gomoku_g37.domain.toOpeningString
import com.example.pdm2324i_gomoku_g37.domain.toVariantString
import com.example.pdm2324i_gomoku_g37.service.utils.PathTemplate
import com.example.pdm2324i_gomoku_g37.service.utils.ProblemJson
import com.example.pdm2324i_gomoku_g37.service.utils.plus
import com.google.gson.Gson
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.net.URI
import java.net.URL
import kotlin.coroutines.resumeWithException

private const val GOMOKU_API_URL = "http://10.0.2.2:8080"

class RealGomokuService(
    private val client: OkHttpClient,
    private val gson: Gson,
    private val baseRequestUrl: URL = URL(GOMOKU_API_URL),
) : GomokuService {

    private val authorsRequest by lazy {
        buildRequest(url = baseRequestUrl + URI(PathTemplate.AUTHORS))
    }

    private val lobbiesRequest by lazy {
        buildRequest(url = baseRequestUrl + URI(PathTemplate.LOBBIES))
    }

    private val apiInfoRequest by lazy {
        buildRequest(url = baseRequestUrl + URI(PathTemplate.ABOUT))
    }

    private fun signUpRequest(username: String, password: String) = lazy {
        buildRequest(
            url = baseRequestUrl + URI(PathTemplate.CREATE_USER),
            method = "POST",
            body = makeSignUpLoginBody(username, password)
        )
    }

    private fun loginRequest(username: String, password: String) = lazy {
        buildRequest(
            url = baseRequestUrl + URI(PathTemplate.LOGIN),
            method = "POST",
            body = makeSignUpLoginBody(username, password)
        )
    }

    private val rankingsRequest by lazy {
        buildRequest(url = baseRequestUrl + URI(PathTemplate.RANKINGS))
    }

    private fun createLobbyRequest(token: String, rules: Rules) = lazy {
        buildRequest(
            url = baseRequestUrl + URI(PathTemplate.START),
            method = "POST",
            body = makeCreateLobbyBody(rules),
            token = token
        )
    }

    private fun joinLobbyRequest(token: String, lobby: WaitingLobby) = lazy {
        buildRequest(
            url = baseRequestUrl + URI(PathTemplate.JOIN_LOBBY),
            method = "POST",
            body = makeJoinLobby(lobby),
            token = token
        )
    }

    private fun leaveLobbyRequest(token: String) = lazy {
        buildRequest(
            url = baseRequestUrl + URI(PathTemplate.LEAVE_LOBBY),
            method = "DELETE",
            token = token
        )
    }

    private fun fetchUserRequest(token: String) = lazy {
        buildRequest(
            url = baseRequestUrl + URI(PathTemplate.USER),
            method = "GET",
            token = token
        )
    }

    private fun isGameCreatedRequest(token: String, lobbyId: String) = lazy {
        buildRequest(
            url = baseRequestUrl + PathTemplate.isGameCreated(lobbyId),
            method = "GET",
            token = token
        )
    }

    override suspend fun fetchAuthors(): List<Author> = authorsRequest.send { body ->
        gson.fromJson<AuthorsDto>(body.string(), AuthorsDtoType.type)
    }.properties.authors

    override suspend fun fetchLobbies(): List<WaitingLobby> = lobbiesRequest.send { body ->
        gson.fromJson<LobbiesDto>(body.string(), LobbiesDtoType.type)
    }.properties.lobbyList

    override suspend fun fetchApiInfo(): String = apiInfoRequest.send { body ->
        gson.fromJson<AboutDto>(body.string(), AboutDtoType.type)
    }.properties.version

    override suspend fun signUp(username: String, password: String): UserInfo =
        signUpRequest(username, password).value.send { body ->
            gson.fromJson<UserInfoDto>(body.string(), UserInfoDtoType.type)
        }.properties.toUserInfo()

    override suspend fun login(username: String, password: String): UserInfo =
        loginRequest(username, password).value.send { body ->
            gson.fromJson<UserInfoDto>(body.string(), UserInfoDtoType.type)
        }.properties.toUserInfo()

    override suspend fun fetchRankings(): List<UserStatistics> = rankingsRequest.send { body ->
        gson.fromJson<RankingsDto>(body.string(), RankingsDtoType.type)
    }.properties.rankingList

    override suspend fun createLobby(token: String, rules: Rules): Flow<WaitingLobby> = flow {
        val result = createLobbyRequest(token, rules).value.send { body ->
            gson.fromJson<LobbyDto>(body.string(), LobbyDtoType.type)
        }.properties.toWaitingLobby()
        emit(result)
    }

    override suspend fun joinLobby(token: String, lobby: WaitingLobby): Flow<ReadyLobby> = flow {
        val result = joinLobbyRequest(token, lobby).value.send { body ->
            gson.fromJson<GameDto>(body.string(), GameDtoType.type)
        }.properties.toGame()
        emit(ReadyLobby(result))
    }


    override suspend fun leaveLobby(token: String, lobbyId: String): String = leaveLobbyRequest(token).value.send { body ->
        gson.fromJson<LeaveLobbyDto>(body.string(), LeaveLobbyDtoType.type).properties.waitMessage
    }

    override suspend fun fetchUser(token: String, userId: String): UserInfo = fetchUserRequest(token).value.send { body ->
        gson.fromJson<UserInfoDto>(body.string(), UserInfoDtoType.type)
    }.properties.toUserInfo()

    override suspend fun isGameCreated(token: String, lobbyId: String): String = isGameCreatedRequest(token, lobbyId).value.send { body ->
        gson.fromJson<IsGameCreatedDto>(body.string(), IsGameCreatedDtoType.type)
    }.properties.waitMessage

    override suspend fun getGameById(token: String, gameId: String): GameInfo {
        TODO("Not yet implemented")
    }

    private suspend fun <T> Request.send(handler: (ResponseBody) -> T): T = suspendCancellableCoroutine { continuation ->
        val call = client.newCall(request = this)
        call.enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                continuation.resumeWithException(FetchGomokuError("Error", e))
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val body = response.body
                    if (!response.isSuccessful || body == null) {
                        handleUnsuccessfulResponse(body, response.code, continuation)
                    } else {
                        continuation.resumeWith(Result.success(handler(body)))
                    }
                } catch (t: Throwable) {
                    continuation.resumeWithException(FetchGomokuError("Error. Remote service returned ${response.code}", t))
                }
            }
        })

        continuation.invokeOnCancellation { call.cancel() }
    }

    private fun <T> handleUnsuccessfulResponse(body: ResponseBody?, code: Int, continuation: CancellableContinuation<T>) {
        val problemJson = gson.fromJson(body?.string(), ProblemJson::class.java)
        if (problemJson == null && code == 401) continuation.resumeWithException(ApiUnauthorizedException())
        else continuation.resumeWithException(ApiErrorException(problemJson = problemJson))
    }

    private fun buildRequest(
        url: URL,
        method: String = "GET",
        body: RequestBody? = null,
        token: String? = null
    ): Request = Request
        .Builder()
        .url(url)
        .also {  requestBuilder ->
            if (token != null) requestBuilder.addHeader("Authorization", "Bearer $token")
            if (method.uppercase() != "GET") requestBuilder.method(method, body)
        }
        .build()

}