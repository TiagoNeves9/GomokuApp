package com.example.pdm2324i_gomoku_g37.service.utils

import java.net.URI
import java.net.URL

object PathTemplate {
    private const val HOME = "/"

    const val START = "/games/start"
    const val LOBBIES = "/lobbies"
    const val LEAVE_LOBBY = "/lobbies/leave"
    const val JOIN_LOBBY = "/lobbies/join"
    const val GAMES = "/games"
    private const val PLAY = "/games/"
    private const val IS_GAME_CREATED = "/lobbies/"
    private const val GAME_BY_ID = "/games/"

    private const val USER_BY_ID = "/users/"
    const val CREATE_USER = "/users/signup"
    const val LOGIN = "/users/login"

    const val RANKINGS = "/rankings"
    private const val USER_RANKING = "/rankings/"

    const val AUTHORS = "/authors"
    const val ABOUT = "/about"

    fun home(): URI = URI(HOME)
    fun createUser(): URI = URI(CREATE_USER)
    fun start(): URI = URI(START)
    fun play(gameId: String): URI = URI(PLAY) + gameId
    fun isGameCreated(lobbyId: String): URI = URI(IS_GAME_CREATED) + lobbyId
    fun gameById(gameId: String): URI = URI(GAME_BY_ID) + gameId
    fun userById(userId: String): URI = URI(USER_BY_ID) + userId
    fun getUserRanking(username: String): URI = URI(USER_RANKING) + username
}

object LinkRelations {
    val HOME = LinkRelation("home")
    val LOBBY = LinkRelation("lobby")
    val SELF = LinkRelation("self")
    val GAME = LinkRelation("current-game")
}

operator fun URL.plus(uri: URI) = URL(this.toString() + uri.toString())

operator fun URI.plus(path: String) = URI(this.toString() + path)