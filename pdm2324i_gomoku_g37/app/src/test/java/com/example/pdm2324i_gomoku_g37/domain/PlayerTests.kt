package com.example.pdm2324i_gomoku_g37.domain

import com.example.pdm2324i_gomoku_g37.domain.board.Piece
import kotlin.test.Test
import kotlin.test.assertSame


class PlayerTests {
    private val playerBlack = Player("PlayerB", Piece.BLACK_PIECE)
    private val playerWhite = Player("PlayerW", Piece.WHITE_PIECE)

    @Test
    fun `Test other Player`() {
        assertSame(playerWhite.color, playerBlack.other())
        assertSame(playerBlack.color, playerWhite.other())
    }

    @Test
    fun `Test Player piece`() {
        assertSame(Piece.BLACK_PIECE, playerBlack.piece())
        assertSame(Piece.WHITE_PIECE, playerWhite.piece())

        assertSame(Piece.WHITE_PIECE, playerBlack.other())
        assertSame(Piece.BLACK_PIECE, playerWhite.other())
    }
}