package com.example.pdm2324i_gomoku_g37.domain.dtos

import android.util.Log
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Turn
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.board.BoardDraw
import com.example.pdm2324i_gomoku_g37.domain.board.BoardRun
import com.example.pdm2324i_gomoku_g37.domain.board.Cell
import com.example.pdm2324i_gomoku_g37.domain.board.Column
import com.example.pdm2324i_gomoku_g37.domain.board.Row
import com.example.pdm2324i_gomoku_g37.domain.board.toCell
import com.example.pdm2324i_gomoku_g37.service.utils.SirenModel
import java.time.Instant

typealias GameDto = SirenModel<GameDtoProperties>

data class GameDtoProperties(
    val id: String,
    val userB: User,
    val userW: User,
    val turn: String,
    val rules: Rules,
    val boardCells: Map<String, Turn>,
    val boardState: String
)

val GameDtoType = SirenModel.getType<GameDtoProperties>()

fun GameDtoProperties.toGame(): Game {
    Log.v("success_gamedto", this.toString())
    val cells: Map<Cell, Turn> = if (boardCells.isEmpty()) emptyMap()
                else boardCells.mapKeys {
                    it.key.toCell(rules.boardDim)
                }

    val board = getBoard(cells, turn, rules.boardDim, userB.username)
    return Game(
        id,
        Pair(userB, userW),
        board,
        getCurrentPlayer(userB, userW, turn),
        rules
    )
}

fun getBoard(boardCells: Map<Cell, Turn>, turn: String, boardDim: Int, userB: String) =
    BoardRun(boardCells, turn.getTurn(userB), boardDim)

fun getCurrentPlayer(userB: User, userW: User, turn: String): Player =
    if (userB.username == turn) Player(userB, Turn.BLACK_PIECE) else Player(userW, Turn.WHITE_PIECE)

fun String.getTurn(userB: String): Turn = if (userB == this) Turn.BLACK_PIECE else Turn.WHITE_PIECE