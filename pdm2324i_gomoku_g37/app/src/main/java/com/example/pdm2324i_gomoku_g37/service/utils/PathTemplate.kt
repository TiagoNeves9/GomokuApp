package com.example.pdm2324i_gomoku_g37.service.utils

import java.net.URI
import java.net.URL
import java.util.*


object PathTemplate {
    const val HOME = "/"

    const val START = "/games/start"
    const val LOBBIES = "/lobbies"
    const val LEAVE_LOBBY = "/lobbies/leave"
    const val JOIN_LOBBY = "/lobbies/join"
    const val GAMES = "/games"
    const val PLAY = "/games/{id}"
    const val SPECTATE = "/games/spectate/{id}"
    const val IS_GAME_CREATED = "/lobbies/{id}"
    const val GAME_BY_ID = "/games/{id}"

    const val USER = "/user"
    const val USERS = "/users"
    const val USER_BY_ID = "/users/{id}"
    const val CREATE_USER = "/users/signup"
    const val LOGIN = "/users/login"
    const val COOKIE = "/users/cookie"

    const val RANKINGS = "/rankings"
    const val USER_RANKING = "/rankings/{username}"

    const val AUTHORS = "/authors"
    const val ABOUT = "/about"

    fun home(): URI = URI(HOME)
    fun createUser(): URI = URI(CREATE_USER)
    fun start(): URI = URI(START)
    fun play(gameId: String): URI = URI(PLAY) + gameId
    fun isGameCreated(lobbyId: String): URI = URI(IS_GAME_CREATED) + lobbyId
    fun gameById(gameId: String): URI = URI(GAME_BY_ID) + gameId
}

object LinkRelations {
    val HOME = LinkRelation("home")
    val LOBBY = LinkRelation("lobby")
    val SELF = LinkRelation("self")
    val GAME = LinkRelation("current-game")
}

operator fun URL.plus(uri: URI) = URL(this.toString() + uri.toString())

operator fun URI.plus(path: String) = URI(this.toString() + path)