package com.example.pdm2324i_gomoku_g37.domain

import com.example.pdm2324i_gomoku_g37.domain.board.Board
import com.example.pdm2324i_gomoku_g37.domain.board.BoardDraw
import com.example.pdm2324i_gomoku_g37.domain.board.BoardRun
import com.example.pdm2324i_gomoku_g37.domain.board.BoardWin
import com.example.pdm2324i_gomoku_g37.domain.board.Cell
import com.example.pdm2324i_gomoku_g37.domain.board.Column
import com.example.pdm2324i_gomoku_g37.domain.board.Row
import com.example.pdm2324i_gomoku_g37.domain.dtos.LocalBoardDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.LocalCellDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.LocalColumnDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.LocalGameInfoDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.LocalPlayerDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.LocalRowDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.LocalRulesDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.LocalUserDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.toBoardDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.toLocalUserDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.toPlayerDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.toRulesDto
import java.time.Instant

data class Game(
    val gameId: String,
    val users: Pair<User, User>,
    val board: Board,
    val currentPlayer: Player,
    val score: Int,
    val now: Instant,
    val rules: Rules
){
    private fun switchTurn() =
        if (currentPlayer.first == users.first) users.second
        else users.first

    fun computeNewGame(cell: Cell): Game {
        val newBoard = this.board.addPiece(cell)

        return if (newBoard.checkWin(cell))
            this.copy(board = BoardWin(newBoard.positions, this.currentPlayer, this.rules.boardDim))
        else if (newBoard.checkDraw(this.board.boardSize))
            this.copy(board = BoardDraw(newBoard.positions, this.rules.boardDim))
        else this.copy(
            board = newBoard,
            currentPlayer = Player(this.switchTurn(), this.currentPlayer.second.other())
        )
    }
}

data class GameInfo(
    val id: String,
    val userB: User,
    val userW: User,
    val turn: String,
    val rules: Rules,
    val boardCells: Map<Cell, Turn>,
    val boardState: String
)

fun GameInfo.toGame(): Game {
    val players = Pair(userB, userW)
    val currentPlayer: Player = if (userB.username == turn) Player(userB, Turn.BLACK_PIECE)
                        else Player(userW, Turn.WHITE_PIECE)
    return Game(
        id,
        players,
        BoardRun(boardCells, currentPlayer.second, rules.boardDim),
        currentPlayer,
        0,
        Instant.now(),
        rules
    )
}

fun Game.toGameDto(): LocalGameInfoDto = LocalGameInfoDto(
    gameId = gameId,
    user1 = users.first.toLocalUserDto(),
    user2 = users.second.toLocalUserDto(),
    board = board.toBoardDto(),
    currentPlayer = currentPlayer.toPlayerDto(),
    score = score,
    now = now,
    rules = rules.toRulesDto()
)