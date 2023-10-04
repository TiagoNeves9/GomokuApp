package com.example.pdm2324i_gomoku_g37.domain.board


enum class Piece {
    BLACK_PIECE, WHITE_PIECE;

    fun other() = if (this == WHITE_PIECE) BLACK_PIECE else WHITE_PIECE
}

/*
enum class PieceRep(val symbol: Char) {
    BLACK_PIECE('B'),
    WHITE_SYMBOL('W'),
}
*/