package com.example.pdm2324i_gomoku_g37.screens.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.dtos.LocalGameInfoDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.toGame
import com.example.pdm2324i_gomoku_g37.domain.toGame
import com.example.pdm2324i_gomoku_g37.screens.common.GAME_EXTRA
import com.example.pdm2324i_gomoku_g37.screens.common.USER_INFO_EXTRA
import com.example.pdm2324i_gomoku_g37.screens.common.UserInfoExtra
import com.example.pdm2324i_gomoku_g37.screens.common.getGameExtra

class GameActivity : ComponentActivity() {
    companion object {
        fun navigateTo(origin: Context, userInfo: UserInfo, game: LocalGameInfoDto) {
            origin.startActivity(createIntent(origin, userInfo, game))
        }

        private fun createIntent(ctx: Context, userInfo: UserInfo, game: LocalGameInfoDto): Intent {
            Log.v("game_activity", game.toString())
            val intent = Intent(ctx, GameActivity::class.java)
            intent.putExtra(USER_INFO_EXTRA, UserInfoExtra(userInfo))
            intent.putExtra(GAME_EXTRA, game)
            return intent
        }
    }

    private val gameExtra: LocalGameInfoDto by lazy {
        checkNotNull(getGameExtra(intent))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GameScreen(game = gameExtra.toGame())
        }
    }
}