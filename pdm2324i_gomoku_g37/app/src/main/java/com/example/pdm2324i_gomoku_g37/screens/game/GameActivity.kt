package com.example.pdm2324i_gomoku_g37.screens.game

import com.example.pdm2324i_gomoku_g37.domain.board.Board
import com.example.pdm2324i_gomoku_g37.domain.board.BoardDraw
import com.example.pdm2324i_gomoku_g37.domain.board.BoardWin
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.board.Cell


data class GameActivity(
    val players: Pair<Player, Player>, val board: Board, val currentPlayer: Player
) {
    private fun switchTurn() =
        if (currentPlayer == players.first) players.second
        else players.first

    fun computeNewGame(cell: Cell): GameActivity {
        val newBoard = this.board.addPiece(cell)

        return if (newBoard.checkWin(cell))
            this.copy(board = BoardWin(newBoard.positions, this.currentPlayer))
        else if (newBoard.checkDraw())
            this.copy(board = BoardDraw(newBoard.positions))
        else this.copy(board = newBoard, currentPlayer = this.switchTurn())
    }
}