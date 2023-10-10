package com.example.pdm2324i_gomoku_g37

import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.board.Piece


val resourceMap: Map<String, Int> = mapOf(
    "img_tiago" to R.drawable.img_tiago,
    "img_tomas" to R.drawable.img_tomas,
    "img_joao" to R.drawable.img_joao,
)

fun Piece.image(): Int =
    if (this == Piece.BLACK_PIECE) R.drawable.b
    else R.drawable.w