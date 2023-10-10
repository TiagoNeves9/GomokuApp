package com.example.pdm2324i_gomoku_g37.domain

import com.example.pdm2324i_gomoku_g37.domain.board.Piece


data class Player(val username: String, val color: Piece) {
    fun other() =
        if (this.color == Piece.WHITE_PIECE) Piece.BLACK_PIECE
        else Piece.WHITE_PIECE

    fun piece(): Piece =
        if (this.color == Piece.BLACK_PIECE) Piece.BLACK_PIECE
        else Piece.WHITE_PIECE
}