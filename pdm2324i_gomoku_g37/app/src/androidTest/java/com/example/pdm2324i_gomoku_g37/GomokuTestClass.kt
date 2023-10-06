package com.example.pdm2324i_gomoku_g37

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pdm2324i_gomoku_g37.domain.Board
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.board.Cell
import com.example.pdm2324i_gomoku_g37.domain.board.Piece
import com.example.pdm2324i_gomoku_g37.domain.createBoard
import com.example.pdm2324i_gomoku_g37.screens.DrawBoard
import com.example.pdm2324i_gomoku_g37.screens.DrawCells
import com.example.pdm2324i_gomoku_g37.screens.GameScreen

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

    @Test
    fun screen_initial_state_display_board() {
        composeTestRule.setContent { GameScreen() }
        composeTestRule.onNodeWithTag("boardTest").assertIsDisplayed()
    }

   @Test
   fun screen_cells_display(){
       val playerBlack = Player("BlackPlayer", Piece.BLACK_PIECE)
       val board = createBoard(playerBlack.color)
       val cell = Cell(0,0)
       composeTestRule.setContent {
           DrawCells(board = board, onClick = {}, cell = cell )
       }

       composeTestRule.onNodeWithTag("cell").assertIsDisplayed()
   }

   @Test
   fun cell_clickable(){
       composeTestRule.setContent {
           GameScreen()
       }

       composeTestRule.onNodeWithTag()
   }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.pdm2324i_gomoku_g37", appContext.packageName)
    }
}