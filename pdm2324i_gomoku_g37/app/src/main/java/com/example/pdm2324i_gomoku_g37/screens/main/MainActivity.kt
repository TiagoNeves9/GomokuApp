package com.example.pdm2324i_gomoku_g37.screens.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Turn
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.domain.board.BOARD_DIM
import com.example.pdm2324i_gomoku_g37.domain.board.createBoard
import com.example.pdm2324i_gomoku_g37.screens.game.GameActivity
import com.example.pdm2324i_gomoku_g37.screens.game.GameScreen
import com.example.pdm2324i_gomoku_g37.screens.login.LoginActivity
import com.example.pdm2324i_gomoku_g37.screens.signup.SignUpActivity
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import java.util.UUID


class MainActivity : ComponentActivity() {
    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, MainActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme {
                MainScreen(
                    onLoginRequested = {
                        LoginActivity.navigateTo(origin = this)
                    },
                    onRegisterRequested = {
                        SignUpActivity.navigateTo(origin = this)
                    }
                )
            }

            /*val playerB = Player(
                User(UUID.randomUUID(), "BlackPlayer", "encPassword1"),
                Turn.BLACK_PIECE
            )
            val playerW = Player(
                User(UUID.randomUUID(), "WhitePlayer", "encPassword2"),
                Turn.WHITE_PIECE
            )

            val board = createBoard(playerB.second, BOARD_DIM)
            val rules = Rules(board.boardSize, Opening.FREESTYLE, Variant.FREESTYLE)
            val game = GameActivity(Pair(playerB.first, playerW.first), board, playerB, rules)

            GameScreen(game)*/
        }
    }
}