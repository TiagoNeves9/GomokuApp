package com.example.pdm2324i_gomoku_g37.domain.board

import com.example.pdm2324i_gomoku_g37.domain.BOARD_DIM


class Column private constructor(val symbol: Char) {
    val index get() = values.indexOf(this)

    companion object {
        val values = List(BOARD_DIM) { Column('A' + it) }

        operator fun invoke(symbol: Char) = values.first { it.symbol == symbol }
    }

    override fun toString() = "Column $symbol."
}

fun Char.toColumnOrNull() = Column.values.find { it.symbol == this }
fun Char.toColumn() =
    this.toColumnOrNull() ?: throw IllegalArgumentException("Invalid column $this.")

fun Int.indexToColumnOrNull(): Column? = Column.values.find { this == it.index }
fun Int.indexToColumn(): Column =
    this.indexToColumnOrNull() ?: throw IllegalArgumentException("Invalid column $this.")