package com.example.pdm2324i_gomoku_g37.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
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
            /*val playerBlack = Player(User("BlackPlayer"), Turn(Piece.BLACK_PIECE))
            val playerWhite = Player(User("WhitePlayer"), Turn(Piece.WHITE_PIECE))
            val board = createBoard(playerBlack.second.color)
            val game = GameActivity(Pair(playerBlack.first, playerWhite.first), board, playerBlack)
            GameScreen(game)*/
        }
    }
}