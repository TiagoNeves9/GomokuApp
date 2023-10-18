package com.example.pdm2324i_gomoku_g37.screens.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.screens.login.LoginActivity
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme


class MainActivity : ComponentActivity() {
    companion object{
        fun navigateTo(origin: LoginActivity) {
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
                    }
                    /* TODO
                    onRegisterRequested = {
                        RegisterActivity.navigateTo(origin = this)
                    }
                    */
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