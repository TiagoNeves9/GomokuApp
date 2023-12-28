package isel.pdm.pdm2324i_gomoku_g37.domain.board

import com.pdm.pdm2324i_gomoku_g37.domain.board.BOARD_DIM
import com.pdm.pdm2324i_gomoku_g37.domain.board.Row
import com.pdm.pdm2324i_gomoku_g37.domain.board.toRow
import com.pdm.pdm2324i_gomoku_g37.domain.board.toRowOrNull
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertSame


class RowTests {
    @Test
    fun `Test row properties`() {
        val sut = Row(4)
        assertEquals(4, sut.number)
        assertEquals(3, sut.index)
        assertEquals("Row 4.", sut.toString())

        val other = Row(4)
        assertEquals(sut, other)
        assertSame(sut, other)
    }

    @Test
    fun `Convert a number to a Row`() {
        assertSame(Row(4), 4.toRowOrNull(BOARD_DIM))
        assertNull(0.toRowOrNull(BOARD_DIM))
        assertNull((BOARD_DIM + 1).toRowOrNull(BOARD_DIM))
        assertSame(Row(4), 4.toRow(BOARD_DIM))

        val ex = assertFailsWith<IllegalArgumentException> { 27.toRow(BOARD_DIM) }
        assertEquals("Invalid row 27.", ex.message)
    }

    @Test
    fun `Row values are correct`() {
        assertEquals(BOARD_DIM, Row.valuesMedium.size)
        assertEquals(0, Row.valuesMedium[0].index)
        assertEquals(BOARD_DIM, Row.valuesMedium[BOARD_DIM - 1].number)
    }
}