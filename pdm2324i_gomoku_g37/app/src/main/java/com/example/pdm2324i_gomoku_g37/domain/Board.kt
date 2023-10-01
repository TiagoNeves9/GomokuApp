package com.example.pdm2324i_gomoku_g37.domain

import com.example.pdm2324i_gomoku_g37.domain.board.Cell
import com.example.pdm2324i_gomoku_g37.domain.board.Piece


const val BOARD_DIM = 15
const val BOARD_CELL_SIZE = 20

sealed class Board(val positions: Map<Cell, Piece>) {
    fun addPiece(cell: Cell): BoardRun {
        check(this is BoardRun) { "Game finished." }

        return if (this.positions[cell] != null)
            throw IllegalArgumentException("Square already occupied!")
        else {
            val newMap: Map<Cell, Piece> = this.positions + mapOf(cell to this.turn)
            BoardRun(newMap, this.turn.other())
        }
    }
}

class BoardRun(positions: Map<Cell, Piece>, val turn: Piece) : Board(positions)

class BoardEnd(positions: Map<Cell, Piece>) : Board(positions)

fun createBoard(firstTurn: Piece) = BoardRun(mapOf(), firstTurn)

//TODO fun Board.getWinner(): Player? {}


val exampleMap = mapOf(
    Cell(0, 0) to Piece.BLACK_PIECE,
    Cell(0, 10) to Piece.BLACK_PIECE,
    Cell(10, 0) to Piece.WHITE_PIECE,
    Cell(15, 15) to Piece.WHITE_PIECE
)