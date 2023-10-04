package com.example.pdm2324i_gomoku_g37.domain.board

import com.example.pdm2324i_gomoku_g37.domain.BOARD_DIM


class Cell private constructor(val row: Row, val col: Column) {
    val rowIndex: Int = row.index
    val colIndex: Int = col.index

    override fun toString(): String =
        if (this == INVALID) "INVALID Cell!" else "${this.row.number}${this.col.symbol}"

    companion object {
        val values =
            List(BOARD_DIM * BOARD_DIM) {
                Cell((it / BOARD_DIM).indexToRow(), (it % BOARD_DIM).indexToColumn())
            }
        val INVALID = Cell(-1, -1)

        operator fun invoke(rowIndex: Int, colIndex: Int): Cell =
            if (rowIndex in 0 until BOARD_DIM && colIndex in 0 until BOARD_DIM)
                values[rowIndex * BOARD_DIM + colIndex]
            else INVALID

        operator fun invoke(row: Row, col: Column): Cell = Cell(row.index, col.index)
    }
}

fun String.toCellOrNull(): Cell? {
    if (this.length != 2) return null
    val row = this.substring(0, this.length - 1).toInt().toRowOrNull()
    val col = this[1].toColumnOrNull()
    return Cell.values.find { it.row == row && it.col == col }
}

operator fun Cell.plus(dir: Direction): Cell =
    Cell(row.index + dir.difRow, col.index + dir.difCol)


fun String.toCell(): Cell =
    this.toCellOrNull() ?: throw IllegalArgumentException(this.getFailReason())

private fun String.getFailReason(): String {
    if (this.length != 2) return "Cell must have a row and a column."

    val rowNr = this.substring(0, this.length - 1)
    val colLetter = this[1]

    return if (rowNr.toInt() !in 1..BOARD_DIM) "Invalid row $rowNr."
    else if (colLetter.code - 'A'.code !in 0 until BOARD_DIM) "Invalid column $colLetter."
    else ""
}

fun cellsInDir(from: Cell, dir: Direction): List<Cell> {
    val line = mutableListOf<Cell>()
    var currentCell = from + dir
    while (currentCell != Cell.INVALID) {
        line.add(currentCell)
        currentCell += dir
    }
    return line
}