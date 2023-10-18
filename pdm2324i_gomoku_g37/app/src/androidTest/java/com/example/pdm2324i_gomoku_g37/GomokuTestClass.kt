package com.example.pdm2324i_gomoku_g37

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class GomokuTestClass {
    @get:Rule
    val composeTestRule = createComposeRule()

    /*
    val playerW = User("Cabral",Piece.WHITE_PIECE)
    val playerB = User("Gyokeres",Piece.BLACK_PIECE)
    val players = Pair<User,User>(playerW,playerB)
    val board = createBoard(playerW.piece())
    val game =  GameActivity(players,board,playerW)
    */
    @Test
    fun screen_initial_state_display_board() {

        //composeTestRule.setContent { GameScreen(game) }
        composeTestRule.onNodeWithTag("boardTest").assertIsDisplayed()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.pdm2324i_gomoku_g37", appContext.packageName)
    }
}