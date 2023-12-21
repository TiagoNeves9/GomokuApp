package com.example.pdm2324i_gomoku_g37.domain.dtos

import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Turn
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.board.BoardDraw
import com.example.pdm2324i_gomoku_g37.domain.board.Cell
import com.example.pdm2324i_gomoku_g37.service.utils.SirenModel
import java.time.Instant

typealias GameDto = SirenModel<GameDtoProperties>

data class GameDtoProperties(
    val id: String,
    val userB: User,
    val userW: User,
    val turn: String,
    val rules: Rules,
    val boardCells: Map<Cell, Turn>,
    val boardState: String
)

val GameDtoType = SirenModel.getType<GameDtoProperties>()

fun GameDtoProperties.toGame() = Game(
    id,
    Pair(userB, userW),
    BoardDraw(boardCells, rules.boardDim),
    if (turn == userB.username) Player(userB, Turn.BLACK_PIECE) else Player(userW, Turn.WHITE_PIECE),
    0,
    Instant.now(),
    rules
)