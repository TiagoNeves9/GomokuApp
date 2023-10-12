package com.example.pdm2324i_gomoku_g37.domain.board

import com.example.pdm2324i_gomoku_g37.domain.Player


const val BOARD_DIM = 15
const val N_ON_ROW = 5
const val BOARD_CELL_SIZE = 21

sealed class Board(val positions: Map<Cell, Piece>) {
    init {
        check(BOARD_DIM >= N_ON_ROW) { "Board dimension must be >= to $N_ON_ROW" }
    }
    fun addPiece(cell: Cell): BoardRun {
        check(this is BoardRun) { "Game finished." }

        //TODO: Catch do error em vez de throw
        return if (this.positions[cell] != null)
            throw IllegalArgumentException("Square already occupied!")
        else {
            val newMap: Map<Cell, Piece> = this.positions + mapOf(cell to this.turn)
            BoardRun(newMap, this.turn.other())
        }
    }
}

class BoardRun(positions: Map<Cell, Piece>, val turn: Piece) : Board(positions) {
    fun checkWin(lastMove: Cell): Boolean =
        positions.size >= 2 * N_ON_ROW - 1 && (
                checkWinInDir(lastMove, Direction.UP, Direction.DOWN) ||
                        checkWinInDir(lastMove, Direction.LEFT, Direction.RIGHT) ||
                        checkWinInDir(lastMove, Direction.UP_LEFT, Direction.DOWN_RIGHT) ||
                        checkWinInDir(lastMove, Direction.UP_RIGHT, Direction.DOWN_LEFT)
                )

    fun checkDraw(): Boolean = positions.size == BOARD_DIM * BOARD_DIM

    private fun checkWinInDir(lastMove: Cell, dir1: Direction, dir2: Direction): Boolean {
        val line =
            cellsInDir(lastMove, dir1).reversed() + lastMove + cellsInDir(lastMove, dir2)
        // we reverse the first part of the list because we want
        // to check the line from left/top to right/bottom
        return checkWinInLine(line)
    }

    private fun checkWinInLine(line: List<Cell>): Boolean {
        var count = 0
        for (cell in line) {
            if (this.positions[cell] == this.turn.other()) {
                count++
                if (count >= N_ON_ROW)
                    return true
            } else count = 0
        }
        return false
    }
}

class BoardWin(positions: Map<Cell, Piece>, val winner: Player) : Board(positions)

class BoardDraw(positions: Map<Cell, Piece>) : Board(positions)


fun createBoard(firstTurn: Piece) = BoardRun(mapOf(), firstTurn)

val exampleMap = mapOf(
    Cell(0, 0) to Piece.BLACK_PIECE,
    Cell(0, 10) to Piece.BLACK_PIECE,
    Cell(10, 0) to Piece.WHITE_PIECE,
    Cell(15, 15) to Piece.WHITE_PIECE
)