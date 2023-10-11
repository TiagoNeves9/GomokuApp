package com.example.pdm2324i_gomoku_g37

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm2324i_gomoku_g37.Authors.AuthorsActivity
import com.example.pdm2324i_gomoku_g37.domain.BOARD_DIM
import com.example.pdm2324i_gomoku_g37.Game.Game
import com.example.pdm2324i_gomoku_g37.domain.N_ON_ROW
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.board.Piece
import com.example.pdm2324i_gomoku_g37.domain.createBoard
import com.example.pdm2324i_gomoku_g37.Game.GameScreen
import com.example.pdm2324i_gomoku_g37.Home.HomeScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        check(BOARD_DIM >= N_ON_ROW) { "Board dimension must be >= to $N_ON_ROW" }
        super.onCreate(savedInstanceState)
        setContent {
            val playerBlack = Player("BlackPlayer", Piece.BLACK_PIECE)
            val playerWhite = Player("WhitePlayer", Piece.WHITE_PIECE)
            val board = createBoard(playerBlack.color)
            val game = Game(Pair(playerBlack, playerWhite), board, playerBlack)
            //GameScreen(game)
            //AuthorsScreen()
            HomeScreen(
                onAuthorsRequest ={
                    AuthorsActivity.navigateTo(this)
                }
            )
        }
    }
}