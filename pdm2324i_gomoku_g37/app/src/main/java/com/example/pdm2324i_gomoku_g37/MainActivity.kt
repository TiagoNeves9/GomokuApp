package com.example.pdm2324i_gomoku_g37

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.Turn
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.board.Piece
import com.example.pdm2324i_gomoku_g37.domain.board.createBoard
import com.example.pdm2324i_gomoku_g37.screens.game.GameActivity
import com.example.pdm2324i_gomoku_g37.screens.game.GameScreen
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.screens.start.StartScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /*StartScreen(
                onHomeRequested = {
                    HomeActivity.navigateTo(origin = this)
                }
            )*/
            val playerBlack = Player(User("BlackPlayer"), Turn(Piece.BLACK_PIECE))
            val playerWhite = Player(User("WhitePlayer"), Turn(Piece.WHITE_PIECE))
            val board = createBoard(playerBlack.second.color)
            val game =
                GameActivity(Pair(playerBlack.first, playerWhite.first), board, playerBlack)
            GameScreen(game)
        }
    }
}