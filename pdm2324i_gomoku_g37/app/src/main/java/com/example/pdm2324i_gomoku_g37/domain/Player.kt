package com.example.pdm2324i_gomoku_g37.domain

import java.util.*


/**
 * Player is a Pair of
 * User (person with an account) and Turn (color of the user's pieces)
 * */
typealias Player = Pair<User, Turn>

data class User(val userId: UUID, val username: String, val encodedPassword: String) {
    /*
    fun other() =
        if (this.color == Piece.WHITE_PIECE) Piece.BLACK_PIECE
        else Piece.WHITE_PIECE

    fun piece(): Piece =
        if (this.color == Piece.BLACK_PIECE) Piece.BLACK_PIECE
        else Piece.WHITE_PIECE
    */
}

enum class Turn {
    BLACK_PIECE, WHITE_PIECE;

    //TODO Does turn needs score?
    fun other() =
        if (this == WHITE_PIECE) BLACK_PIECE
        else WHITE_PIECE
}