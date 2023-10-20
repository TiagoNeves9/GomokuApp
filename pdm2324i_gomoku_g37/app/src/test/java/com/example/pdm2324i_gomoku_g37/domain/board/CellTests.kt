package com.example.pdm2324i_gomoku_g37.domain.board

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertSame


class CellTests {
    @Test
    fun `Get a cell by Row and Column and test properties`() {
        val sut = Cell(Row(1), Column('B'))
        assertEquals(Row(1), sut.row)
        assertEquals(Column('B'), sut.col)
        assertEquals(0, sut.rowIndex)
        assertEquals(1, sut.colIndex)
        assertEquals("1B", sut.toString())
    }

    @Test
    fun `Get a cell by Row index and Column index`() {
        val sut = Cell(0, 1, BOARD_DIM)
        assertEquals(Row(1), sut.row)
        assertEquals(Column('B'), sut.col)
        assertEquals(0, sut.rowIndex)
        assertEquals(1, sut.colIndex)
        assertEquals("1B", sut.toString())
    }

    @Test
    fun `Get a Cell instance`() {
        val sut = Cell(Row(2), Column('D'))
        assertEquals(sut, Cell(1, 3, BOARD_DIM))
        assertSame(sut, Cell(1, 3, BOARD_DIM))
    }

    @Test
    fun `Get all valid cells`() {
        assertEquals(BOARD_DIM * BOARD_DIM, Cell.valuesMedium.size)
        assertEquals(Cell(Row(1), Column('A')), Cell.valuesMedium[0])
        assertEquals(
            Cell(Row(BOARD_DIM), Column('A' + BOARD_DIM - 1)),
            Cell.valuesMedium[BOARD_DIM * BOARD_DIM - 1]
        )
    }

    @Test
    fun `Get an invalid Cell by indexes`() {
        assertEquals(Cell.INVALID, Cell(-1, 2, BOARD_DIM))
        assertEquals(Cell.INVALID, Cell(2, BOARD_DIM, BOARD_DIM))
    }

    @Test
    fun `Parse a string to a Cell`() {
        val sut = Cell(0, 1, BOARD_DIM)
        assertEquals(sut, "1B".toCellOrNull(BOARD_DIM))
        assertSame(sut, "1B".toCellOrNull(BOARD_DIM))
        assertNull("1".toCellOrNull(BOARD_DIM))
        assertNull("B".toCellOrNull(BOARD_DIM))
        assertNull("1BB".toCellOrNull(BOARD_DIM))
        assertNull("1Z".toCellOrNull(BOARD_DIM))
        assertEquals(sut, "1B".toCell(BOARD_DIM))

        val ex = assertFailsWith<IllegalArgumentException> { "1CC".toCell(BOARD_DIM) }
        assertEquals("Cell must have a row and a column.", ex.message)
        assertEquals(
            "Invalid column Z.",
            assertFailsWith<IllegalArgumentException> { "1Z".toCell(BOARD_DIM) }.message
        )
        assertEquals(
            "Invalid row 0.",
            assertFailsWith<IllegalArgumentException> { "0B".toCell(BOARD_DIM) }.message
        )
    }

    @Test
    fun `Add a cell and a direction`() {
        assertEquals(
            Cell(0, 1, BOARD_DIM),
            Cell(0, 0, BOARD_DIM).plus(Direction.RIGHT, BOARD_DIM)
        )
        assertEquals(
            Cell(1, 1, BOARD_DIM),
            Cell(0, 0, BOARD_DIM).plus(Direction.DOWN_RIGHT, BOARD_DIM)
        )
        assertEquals(
            Cell(1, BOARD_DIM - 2, BOARD_DIM),
            Cell(0, BOARD_DIM - 1, BOARD_DIM).plus(Direction.DOWN_LEFT, BOARD_DIM)
        )
        assertEquals(
            Cell.INVALID,
            Cell(0, 0, BOARD_DIM).plus(Direction.UP, BOARD_DIM)
        )
        assertEquals(
            Cell.INVALID,
            Cell(0, BOARD_DIM - 1, BOARD_DIM).plus(Direction.DOWN_RIGHT, BOARD_DIM)
        )
    }

    @Test
    fun `Get line of cells from a cell and a direction`() {
        val from = Cell(3, 2, BOARD_DIM)
        val dir = Direction.LEFT
        val line1 = cellsInDir(from, dir, BOARD_DIM)
        assertEquals(
            listOf(from.plus(dir, BOARD_DIM), from.plus(dir, BOARD_DIM).plus(dir, BOARD_DIM)),
            line1
        )

        val line2 = cellsInDir(from, Direction.DOWN_RIGHT, BOARD_DIM)
        assertEquals(
            List(BOARD_DIM - 4) { Cell(3 + it + 1, 2 + it + 1, BOARD_DIM) },
            line2
        )
    }
}