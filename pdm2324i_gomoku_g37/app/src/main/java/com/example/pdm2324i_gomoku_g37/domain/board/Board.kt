package com.example.pdm2324i_gomoku_g37.domain.board

import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Turn
import com.example.pdm2324i_gomoku_g37.domain.Variant


const val BOARD_DIM = 15
const val BIG_BOARD_DIM = 19
const val N_ON_ROW = 5
const val BOARD_CELL_SIZE = 21

sealed class Board(val positions: Map<Cell, Turn>, val boardSize: Int) {
    init {
        check(boardSize >= N_ON_ROW) { "Board dimension must be >= to $N_ON_ROW" }
    }

    fun addPiece(cell: Cell, rules: Rules): BoardRun {
        check(this is BoardRun) { "Game finished." }

        //TODO: Catch do error em vez de throw
        if (cell.toString() in this.positions.map { it.key.toString() })
            throw IllegalArgumentException("Square already occupied!")
        else {
            val opening = rules.opening
            val respectOpening: Boolean =
                opening ==
                        Opening.FREESTYLE || (
                        opening == Opening.PRO && opening.isProOpening(this, cell)
                        )

            if (respectOpening) {
                val newMap: Map<Cell, Turn> = this.positions + mapOf(cell to this.turn)
                return BoardRun(newMap, this.turn.other(), boardSize)
            } else throw IllegalArgumentException("Invalid move!")
        }
    }
}

class BoardRun(positions: Map<Cell, Turn>, val turn: Turn, boardSize: Int) :
    Board(positions, boardSize) {
    fun checkWin(lastMove: Cell): Boolean =
        positions.size >= 2 * N_ON_ROW - 1 && (
                checkWinInDir(lastMove, Direction.UP, Direction.DOWN, boardSize) ||
                        checkWinInDir(lastMove, Direction.LEFT, Direction.RIGHT, boardSize) ||
                        checkWinInDir(
                            lastMove,
                            Direction.UP_LEFT,
                            Direction.DOWN_RIGHT,
                            boardSize
                        ) ||
                        checkWinInDir(lastMove, Direction.UP_RIGHT, Direction.DOWN_LEFT, boardSize)
                )

    fun checkDraw(boardSize: Int): Boolean = positions.size == boardSize * boardSize

    private fun checkWinInDir(
        lastMove: Cell, dir1: Direction, dir2: Direction, boardSize: Int
    ): Boolean {
        val line =
            cellsInDir(lastMove, dir1, boardSize).reversed() +
                    lastMove +
                    cellsInDir(lastMove, dir2, boardSize)
        /*  we reverse the first part of the list because we want
            to check the line from left/top to right/bottom     */
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

class BoardWin(positions: Map<Cell, Turn>, val winner: Player, boardSize: Int) :
    Board(positions, boardSize)

class BoardDraw(positions: Map<Cell, Turn>, boardSize: Int) : Board(positions, boardSize)

fun createBoard(firstTurn: Turn = Turn.BLACK_PIECE, boardSize: Int) =
    BoardRun(mapOf(), firstTurn, boardSize)

val exampleMap = mapOf(
    "1A".toCell(BOARD_DIM) to Turn.BLACK_PIECE,
    "5C".toCell(BOARD_DIM) to Turn.WHITE_PIECE,
    "2B".toCell(BOARD_DIM) to Turn.BLACK_PIECE,
    "5D".toCell(BOARD_DIM) to Turn.WHITE_PIECE,
    "11A".toCell(BOARD_DIM) to Turn.BLACK_PIECE,
    "14E".toCell(BOARD_DIM) to Turn.WHITE_PIECE
)