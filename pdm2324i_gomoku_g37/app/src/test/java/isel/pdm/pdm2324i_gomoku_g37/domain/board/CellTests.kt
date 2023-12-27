package isel.pdm.pdm2324i_gomoku_g37.domain.board

import com.pdm.pdm2324i_gomoku_g37.domain.board.BOARD_DIM
import com.pdm.pdm2324i_gomoku_g37.domain.board.Cell
import com.pdm.pdm2324i_gomoku_g37.domain.board.Column
import com.pdm.pdm2324i_gomoku_g37.domain.board.Direction
import com.pdm.pdm2324i_gomoku_g37.domain.board.Row
import com.pdm.pdm2324i_gomoku_g37.domain.board.cellsInDir
import com.pdm.pdm2324i_gomoku_g37.domain.board.distance
import com.pdm.pdm2324i_gomoku_g37.domain.board.plus
import com.pdm.pdm2324i_gomoku_g37.domain.board.toCell
import com.pdm.pdm2324i_gomoku_g37.domain.board.toCellOrNull
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue


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

    @Test
    fun `Test distance between two cells horizontally and vertically`() {
        val from = "8H".toCell(BOARD_DIM) // "8H" is the center of the board
        val to0 = "8G".toCell(BOARD_DIM)
        val to1 = "8I".toCell(BOARD_DIM)
        val to2 = "7H".toCell(BOARD_DIM)
        val to3 = "9H".toCell(BOARD_DIM)
        assertEquals(0, from.distance(from))
        assertEquals(1, from.distance(to0))
        assertEquals(1, from.distance(to1))
        assertEquals(1, from.distance(to2))
        assertEquals(1, from.distance(to3))

        val to4 = "1H".toCell(BOARD_DIM)
        assertEquals(7, from.distance(to4))
    }

    @Test
    fun `Test distance between two cells on diagonally`() {
        val from = "8H".toCell(BOARD_DIM) // "8H" is the center of the board
        val to0 = "7G".toCell(BOARD_DIM)
        val to1 = "7I".toCell(BOARD_DIM)
        val to2 = "9G".toCell(BOARD_DIM)
        val to3 = "9I".toCell(BOARD_DIM)
        assertEquals(1, from.distance(to0))
        assertEquals(1, from.distance(to1))
        assertEquals(1, from.distance(to2))
        assertEquals(1, from.distance(to3))

        val to4 = "6G".toCell(BOARD_DIM)
        val to5 = "10I".toCell(BOARD_DIM)
        assertEquals(2, from.distance(to4))
        assertEquals(2, from.distance(to5))

        val to6 = "1C".toCell(BOARD_DIM)
        assertEquals(7, from.distance(to6))
    }

    @Test
    fun `Test the legal distances for PRO opening rule`() {
        /** The first player's first stone must be placed in the center of the board.
         *  The second player's first stone may be placed anywhere on the board.
         *  The first player's second stone must be placed at least three intersections
         *  away from the first stone (two empty intersections in between the two stones).
         *  */
        val centralCell = "8H".toCell(BOARD_DIM)
        val to0 = "8F"
        val to1 = "8G"
        val to2 = "8I"
        val to3 = "8J"
        val to4 = "6H"
        val to5 = "7H"
        val to6 = "9H"
        val to7 = "10H"
        val to8 = "6F"

        val someIllegalMoves = listOf(to0, to1, to2, to3, to4, to5, to6, to7, to8)
        someIllegalMoves.forEach {
            assertTrue { centralCell.distance(it.toCell(BOARD_DIM)) < 3 }
        }

        val someLegalMoves = listOf("5E", "8E", "1A", "14K")
        someLegalMoves.forEach {
            assertTrue { centralCell.distance(it.toCell(BOARD_DIM)) >= 3 }
        }
    }
}