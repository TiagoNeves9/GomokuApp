package com.example.pdm2324i_gomoku_g37.domain

import com.example.pdm2324i_gomoku_g37.domain.board.Board
import java.time.Instant

data class Game(
    val gameId: String,
    val users: Pair<Player, Player>,
    val board: Board,
    val currentPlayer: Player,
    val score: Int,
    val now: Instant,
    val rules: Rules
)