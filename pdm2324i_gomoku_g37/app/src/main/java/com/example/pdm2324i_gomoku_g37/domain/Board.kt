package com.example.pdm2324i_gomoku_g37.domain

import com.example.pdm2324i_gomoku_g37.domain.board.Cell
import com.example.pdm2324i_gomoku_g37.domain.board.Direction
import com.example.pdm2324i_gomoku_g37.domain.board.Piece
import com.example.pdm2324i_gomoku_g37.domain.board.cellsInDir


const val BOARD_DIM = 15
const val N_ON_ROW = 5
const val BOARD_CELL_SIZE = 20

sealed class Board(val positions: Map<Cell, Piece>) {
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
                checkWinInDirection(lastMove, Direction.UP, Direction.DOWN) ||
                        checkWinInDirection(lastMove, Direction.LEFT, Direction.RIGHT) ||
                        checkWinInDirection(lastMove, Direction.UP_LEFT, Direction.DOWN_RIGHT) ||
                        checkWinInDirection(lastMove, Direction.UP_RIGHT, Direction.DOWN_LEFT)
                )

    private fun checkWinInDirection(lastMove: Cell, dir1: Direction, dir2: Direction): Boolean {
        val line =
            cellsInDir(lastMove, dir1).reversed() + lastMove + cellsInDir(lastMove, dir2)
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


class BoardEnd(positions: Map<Cell, Piece>) : Board(positions)

fun createBoard(firstTurn: Piece) = BoardRun(mapOf(), firstTurn)

//TODO fun Board.getWinner(): Player? {}


val exampleMap = mapOf(
    Cell(0, 0) to Piece.BLACK_PIECE,
    Cell(0, 10) to Piece.BLACK_PIECE,
    Cell(10, 0) to Piece.WHITE_PIECE,
    Cell(15, 15) to Piece.WHITE_PIECE
)