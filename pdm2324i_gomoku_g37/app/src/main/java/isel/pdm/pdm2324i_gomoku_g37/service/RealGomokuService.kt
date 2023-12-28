package isel.pdm.pdm2324i_gomoku_g37.service

import isel.pdm.pdm2324i_gomoku_g37.domain.Author
import isel.pdm.pdm2324i_gomoku_g37.domain.Game
import isel.pdm.pdm2324i_gomoku_g37.domain.ReadyLobby
import isel.pdm.pdm2324i_gomoku_g37.domain.Rules
import isel.pdm.pdm2324i_gomoku_g37.domain.User
import isel.pdm.pdm2324i_gomoku_g37.domain.UserInfo
import isel.pdm.pdm2324i_gomoku_g37.domain.UserStatistics
import isel.pdm.pdm2324i_gomoku_g37.domain.WaitingLobby
import isel.pdm.pdm2324i_gomoku_g37.domain.board.Cell
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.AboutDto
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.AboutDtoType
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.AuthorsDto
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.AuthorsDtoType
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.GameDto
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.GameDtoType
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.IsGameCreatedDto
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.IsGameCreatedDtoType
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.LeaveLobbyDto
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.LeaveLobbyDtoType
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.LobbiesDto
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.LobbiesDtoType
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.LobbyDto
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.LobbyDtoType
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.RankingsDto
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.RankingsDtoType
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.UserDtoProperties
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.UserInfoDto
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.UserInfoDtoType
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.toGame
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.toUser
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.toUserInfo
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.toWaitingLobby
import isel.pdm.pdm2324i_gomoku_g37.service.utils.PathTemplate
import isel.pdm.pdm2324i_gomoku_g37.service.utils.ProblemJson
import isel.pdm.pdm2324i_gomoku_g37.service.utils.plus
import com.google.gson.Gson
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.UserRankingDto
import isel.pdm.pdm2324i_gomoku_g37.domain.dtos.UserRankingDtoType
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

    private fun fetchUserRequest(userId: String) = lazy {
        buildRequest(
            url = baseRequestUrl + PathTemplate.userById(userId),
            method = "GET",
        )
    }

    private fun isGameCreatedRequest(token: String, lobbyId: String) = lazy {
        buildRequest(
            url = baseRequestUrl + PathTemplate.isGameCreated(lobbyId),
            method = "GET",
            token = token
        )
    }

    private fun getGameByIdRequest(token: String, gameId: String) = lazy {
        buildRequest(
            url = baseRequestUrl + PathTemplate.gameById(gameId),
            method = "GET",
            token = token
        )
    }

    private fun playRequest(token: String, gameId: String, cell: Cell, boardSize: Int) = lazy {
        buildRequest(
            url = baseRequestUrl + PathTemplate.play(gameId),
            method = "POST",
            body = makePlayBody(cell, boardSize),
            token = token
        )
    }

    private fun getUserRankingRequest(username: String) = lazy {
        buildRequest(
            url = baseRequestUrl + PathTemplate.getUserRanking(username),
            method = "GET"
        )
    }

    override suspend fun fetchAuthors(): List<Author> = authorsRequest.send { body ->
        gson.fromJson<AuthorsDto>(body.string(), AuthorsDtoType.type)
    }.properties.authors
    //This fetches the authors from the DAW's project, not from PDM's project

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
        }.properties
        emit(ReadyLobby(result.toGame()))
    }

    override suspend fun leaveLobby(token: String, lobbyId: String): String =
        leaveLobbyRequest(token).value.send { body ->
            gson.fromJson<LeaveLobbyDto>(
                body.string(),
                LeaveLobbyDtoType.type
            ).properties.waitMessage
        }

    override suspend fun fetchUserAccount(userId: String): User =
        fetchUserRequest(userId).value.send { body ->
            gson.fromJson(body.string(), UserDtoProperties::class.java)
        }.toUser()

    override suspend fun isGameCreated(token: String, lobbyId: String): String =
        isGameCreatedRequest(token, lobbyId).value.send { body ->
            gson.fromJson<IsGameCreatedDto>(body.string(), IsGameCreatedDtoType.type)
        }.properties.waitMessage

    override suspend fun getGameById(token: String, gameId: String): Game =
        getGameByIdRequest(token, gameId).value.send { body ->
            gson.fromJson<GameDto>(body.string(), GameDtoType.type)
        }.properties.toGame()

    override suspend fun play(token: String, gameId: String, cell: Cell, boardSize: Int): Game =
        playRequest(token, gameId, cell, boardSize).value.send { body ->
            gson.fromJson<GameDto>(body.string(), GameDtoType.type)
        }.properties.toGame()

    override suspend fun userRanking(username: String): UserStatistics =
        getUserRankingRequest(username).value.send { body ->
            gson.fromJson<UserRankingDto>(body.string(), UserRankingDtoType.type)
        }.properties.userRanking

    private suspend fun <T> Request.send(handler: (ResponseBody) -> T): T =
        suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request = this)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) =
                    continuation.resumeWithException(FetchGomokuError("Error", e))

                override fun onResponse(call: Call, response: Response) =
                    try {
                        val body = response.body
                        if (!response.isSuccessful || body == null)
                            handleUnsuccessfulResponse(body, response.code, continuation)
                        else
                            continuation.resumeWith(Result.success(handler(body)))
                    } catch (t: Throwable) {
                        continuation.resumeWithException(
                            FetchGomokuError(
                                "Error. Remote service returned ${response.code}",
                                t
                            )
                        )
                    }
            })
            continuation.invokeOnCancellation { call.cancel() }
        }

    private fun <T> handleUnsuccessfulResponse(
        body: ResponseBody?,
        code: Int,
        continuation: CancellableContinuation<T>
    ) {
        val problemJson = gson.fromJson(body?.string(), ProblemJson::class.java)

        if (code == 401)
            continuation.resumeWithException(ApiUnauthorizedException())
        else if (problemJson != null)
            continuation.resumeWithException(ApiErrorException(problemJson = problemJson))
        else
            continuation.resumeWithException(
                FetchGomokuError(
                    message = "Error. Remote service returned $code",
                    cause = null
                )
            )
    }

    private fun buildRequest(
        url: URL,
        method: String = "GET",
        body: RequestBody? = null,
        token: String? = null
    ): Request = Request
        .Builder()
        .url(url)
        .also { requestBuilder ->
            if (token != null)
                requestBuilder.addHeader("Authorization", "Bearer $token")
            if (method.uppercase() != "GET")
                requestBuilder.method(method, body)
        }
        .build()
}