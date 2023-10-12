package com.example.pdm2324i_gomoku_g37

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pdm2324i_gomoku_g37.screens.game.GameActivity
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.board.Piece
import com.example.pdm2324i_gomoku_g37.domain.board.createBoard
import com.example.pdm2324i_gomoku_g37.screens.game.GameScreen
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class GomokuTestClass {
    @get:Rule
    val composeTestRule = createComposeRule()
    val playerW = Player("Cabral",Piece.WHITE_PIECE)
    val playerB = Player("Gyokeres",Piece.BLACK_PIECE)
    val players = Pair<Player,Player>(playerW,playerB)
    val board = createBoard(playerW.piece())
    val game =  GameActivity(players,board,playerW)
    @Test
    fun screen_initial_state_display_board() {

        composeTestRule.setContent { GameScreen(game) }
        composeTestRule.onNodeWithTag("boardTest").assertIsDisplayed()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.pdm2324i_gomoku_g37", appContext.packageName)
    }
}