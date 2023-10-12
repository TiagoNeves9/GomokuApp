package com.example.pdm2324i_gomoku_g37.domain

import com.example.pdm2324i_gomoku_g37.domain.board.Piece


/**
 * Player is a Pair of
 * User (person with an account, for now just the username) and Turn (color of the piece)
 * */
typealias Player = Pair<User, Turn>

data class User(val username: String)
//TODO Create Token/UserId and Password

data class Turn(val color: Piece) {
    //TODO Does turn needs score?
    /*
    fun other() =
        if (this.color == Piece.WHITE_PIECE) Piece.BLACK_PIECE
        else Piece.WHITE_PIECE

    fun piece(): Piece =
        if (this.color == Piece.BLACK_PIECE) Piece.BLACK_PIECE
        else Piece.WHITE_PIECE
     */
}