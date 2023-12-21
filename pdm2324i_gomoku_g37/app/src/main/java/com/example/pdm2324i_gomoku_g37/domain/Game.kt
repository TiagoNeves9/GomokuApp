package com.example.pdm2324i_gomoku_g37.domain

import com.example.pdm2324i_gomoku_g37.domain.board.Board
import com.example.pdm2324i_gomoku_g37.domain.board.BoardDraw
import com.example.pdm2324i_gomoku_g37.domain.board.BoardRun
import com.example.pdm2324i_gomoku_g37.domain.board.BoardWin
import com.example.pdm2324i_gomoku_g37.domain.board.Cell
import com.example.pdm2324i_gomoku_g37.domain.dtos.LocalGameInfoDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.toBoardDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.toLocalUserDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.toPlayerDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.toRulesDto

data class Game(
    val gameId: String, val users: Pair<User, User>, val board: Board,
    val currentPlayer: Player, val rules: Rules
) {
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

fun Game.toLocalGameInfoDto(): LocalGameInfoDto = LocalGameInfoDto(
    gameId = gameId,
    userW = users.first.toLocalUserDto(),
    userB = users.second.toLocalUserDto(),
    board = board.toBoardDto(),
    currentPlayer = currentPlayer.toPlayerDto(),
    rules = rules.toRulesDto()
)