package com.example.pdm2324i_gomoku_g37.domain.dtos

import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Turn
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.board.BoardDraw
import com.example.pdm2324i_gomoku_g37.domain.board.BoardRun
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

fun GameDto.toGame(): Game {
    val board = getBoard(properties.boardCells, properties.turn, properties.rules.boardDim, properties.userB.username)
    return Game(
        properties.id,
        Pair(properties.userB, properties.userW),
        board,
        getCurrentPlayer(properties.userB, properties.userW, properties.turn),
        properties.rules
    )
}

fun getBoard(boardCells: Map<Cell, Turn>, turn: String, boardDim: Int, userB: String) =
    BoardRun(boardCells, turn.getTurn(userB), boardDim)

fun getCurrentPlayer(userB: User, userW: User, turn: String): Player =
    if (userB.username == turn) Player(userB, Turn.BLACK_PIECE) else Player(userW, Turn.WHITE_PIECE)

fun String.getTurn(userB: String): Turn = if (userB == this) Turn.BLACK_PIECE else Turn.WHITE_PIECE