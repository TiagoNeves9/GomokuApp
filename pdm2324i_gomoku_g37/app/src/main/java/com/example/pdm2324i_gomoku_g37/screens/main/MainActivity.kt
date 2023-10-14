package com.example.pdm2324i_gomoku_g37.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme {
                MainScreen(
                    onHomeRequested = {
                        HomeActivity.navigateTo(origin = this)
                    }
                )
            }
            /*
            val playerBlack = Player(
                User(UUID.randomUUID(), "BlackPlayer", "encPassword1"),
                Turn.BLACK_PIECE
            )
            val playerWhite = Player(
                User(UUID.randomUUID(), "WhitePlayer", "encPassword2"),
                Turn.WHITE_PIECE
            )

            val board = createBoard(playerBlack.second)
            val game = GameActivity(Pair(playerBlack.first, playerWhite.first), board, playerBlack)
            GameScreen(game)
            */
        }
    }
}