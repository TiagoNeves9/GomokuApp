package com.example.pdm2324i_gomoku_g37.domain


/**
 * Player is a Pair of
 * User (person with an account) and Turn (color of the user's pieces)
 * */
typealias Player = Pair<User, Turn>

data class User(val id: String, val username: String)

enum class Turn {
    BLACK_PIECE, WHITE_PIECE;

    //TODO Does turn needs score?
    fun other() =
        if (this == WHITE_PIECE) BLACK_PIECE
        else WHITE_PIECE
}