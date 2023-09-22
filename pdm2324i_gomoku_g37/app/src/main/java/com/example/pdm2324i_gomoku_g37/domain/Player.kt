package com.example.pdm2324i_gomoku_g37.domain

data class Player(val username: String, val color: PieceColor){
    fun other() = if (this.color == PieceColor.WHITE) PieceColor.BLACK else PieceColor.WHITE
}