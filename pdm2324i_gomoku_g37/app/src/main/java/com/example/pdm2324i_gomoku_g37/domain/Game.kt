package com.example.pdm2324i_gomoku_g37.domain

import com.example.pdm2324i_gomoku_g37.domain.board.Cell


data class Game(val players: Pair<Player, Player>, val board: Board, val currentPlayer: Player) {
    private fun switchRound() = if (currentPlayer == players.first) players.second else players.first

    fun computeNewGame(cell: Cell): Game {
        val newBoard = this.board.addPiece(cell)

        return if (newBoard.checkWin(cell)) this.copy(board = BoardEnd(newBoard.positions))
                else this.copy(board = newBoard, currentPlayer = this.switchRound())
    }
}