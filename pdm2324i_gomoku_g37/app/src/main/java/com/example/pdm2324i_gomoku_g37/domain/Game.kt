package com.example.pdm2324i_gomoku_g37.domain

import com.example.pdm2324i_gomoku_g37.domain.board.Board

data class Game(val player1: Player, val player2: Player, val board: Board, val rules: Rules)