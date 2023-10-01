package com.example.pdm2324i_gomoku_g37

import com.example.pdm2324i_gomoku_g37.domain.BoardRun
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.board.Cell
import com.example.pdm2324i_gomoku_g37.domain.board.Column
import com.example.pdm2324i_gomoku_g37.domain.board.Piece
import com.example.pdm2324i_gomoku_g37.domain.board.Row
import com.example.pdm2324i_gomoku_g37.domain.createBoard
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_start_board() {
        val player = Player("tbm", Piece.BLACK_PIECE)
        val boardAfterMove = mapOf<Cell, Piece>() + mapOf<Cell, Piece>(Cell(0, 0) to Piece.BLACK_PIECE)
        val board = createBoard(player)
        val newBoard = board.addPiece(Cell(0, 0))
        assertEquals(boardAfterMove.entries, newBoard)
    }
}