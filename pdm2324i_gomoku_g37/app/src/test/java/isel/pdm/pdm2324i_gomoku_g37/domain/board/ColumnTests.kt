package isel.pdm.pdm2324i_gomoku_g37.domain.board

import com.pdm.pdm2324i_gomoku_g37.domain.board.BOARD_DIM
import com.pdm.pdm2324i_gomoku_g37.domain.board.Column
import com.pdm.pdm2324i_gomoku_g37.domain.board.toColumn
import com.pdm.pdm2324i_gomoku_g37.domain.board.toColumnOrNull
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertSame


class ColumnTests {
    @Test
    fun `Test column properties`() {
        val sut = Column('D')
        assertEquals('D', sut.symbol)
        assertEquals(3, sut.index)
        assertEquals("Column D.", sut.toString())

        val other = Column('D')
        assertEquals(sut, other)
        assertSame(sut, other)
    }

    @Test
    fun `Convert a symbol to a Column`() {
        assertSame(Column('B'), 'B'.toColumnOrNull(BOARD_DIM))
        assertNull('3'.toColumnOrNull(BOARD_DIM))
        assertNull(('A' + BOARD_DIM).toColumnOrNull(BOARD_DIM))
        assertSame(Column('C'), 'C'.toColumn(BOARD_DIM))

        val ex = assertFailsWith<IllegalArgumentException> { '?'.toColumn(BOARD_DIM) }
        assertEquals("Invalid column ?.", ex.message)
    }

    @Test
    fun `Column values are correct`() {
        assertEquals(BOARD_DIM, Column.valuesMedium.size)
        assertEquals(0, Column.valuesMedium[0].index)
        assertEquals('A' + BOARD_DIM - 1, Column.valuesMedium[BOARD_DIM - 1].symbol)
    }
}