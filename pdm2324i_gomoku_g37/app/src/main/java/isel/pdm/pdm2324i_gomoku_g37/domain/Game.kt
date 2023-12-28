package isel.pdm.pdm2324i_gomoku_g37.domain

import isel.pdm.pdm2324i_gomoku_g37.domain.board.Board
import isel.pdm.pdm2324i_gomoku_g37.domain.board.BoardDraw
import isel.pdm.pdm2324i_gomoku_g37.domain.board.BoardWin
import isel.pdm.pdm2324i_gomoku_g37.domain.board.Cell


data class Game(
    val gameId: String,
    val users: Pair<User, User>,
    val board: Board,
    val currentPlayer: Player,
    val rules: Rules
) {
    private fun switchTurn() =
        if (currentPlayer.first == users.first) users.second
        else users.first

    fun computeNewGame(cell: Cell): Game {
        val newBoard = this.board.addPiece(cell)

        return if (newBoard.checkWin(cell))
            this.copy(
                board = BoardWin(newBoard.positions, this.currentPlayer, this.rules.boardDim)
            )
        else if (newBoard.checkDraw(this.board.boardSize))
            this.copy(
                board = BoardDraw(newBoard.positions, this.rules.boardDim)
            )
        else this.copy(
            board = newBoard,
            currentPlayer = Player(
                this.switchTurn(),
                this.currentPlayer.second.other()
            )
        )
    }
}