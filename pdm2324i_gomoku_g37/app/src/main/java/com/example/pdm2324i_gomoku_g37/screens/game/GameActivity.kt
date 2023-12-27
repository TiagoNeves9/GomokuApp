package com.example.pdm2324i_gomoku_g37.screens.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.pdm2324i_gomoku_g37.GomokuDependenciesContainer
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.Loaded
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.board.BoardDraw
import com.example.pdm2324i_gomoku_g37.domain.board.BoardWin
import com.example.pdm2324i_gomoku_g37.domain.getOrNull
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.screens.common.GAME_EXTRA
import com.example.pdm2324i_gomoku_g37.screens.common.GameExtra
import com.example.pdm2324i_gomoku_g37.screens.common.USER_INFO_EXTRA
import com.example.pdm2324i_gomoku_g37.screens.common.UserInfoExtra
import com.example.pdm2324i_gomoku_g37.screens.common.getGameExtra
import com.example.pdm2324i_gomoku_g37.screens.common.getUserInfoExtra
import com.example.pdm2324i_gomoku_g37.screens.common.toUserInfo
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import kotlinx.coroutines.launch

class GameActivity : ComponentActivity() {

    /**
     * Helper method to get the user info extra from the intent.
     */
    private val userInfoExtra: UserInfo by lazy {
        checkNotNull(getUserInfoExtra(intent)).toUserInfo()
    }

    private val gameExtra: Game by lazy {
        checkNotNull(getGameExtra(intent)).toGame()
    }

    private val dependencies by lazy { application as GomokuDependenciesContainer }

    private val viewModel by viewModels<GameScreenViewModel> {
        GameScreenViewModel.factory(dependencies.gomokuService, userInfoExtra, gameExtra)
    }

    companion object {
        fun navigateTo(origin: Context, userInfo: UserInfo, game: GameExtra) {
            origin.startActivity(createIntent(origin, userInfo, game))
        }

        private fun createIntent(ctx: Context, userInfo: UserInfo, game: GameExtra): Intent {
            val intent = Intent(ctx, GameActivity::class.java)
            intent.putExtra(USER_INFO_EXTRA, UserInfoExtra(userInfo))
            intent.putExtra(GAME_EXTRA, game)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetchGame()
                viewModel.currentGameFlow.collect{
                    if (it is Loaded) {
                        doNavigation(it.getOrNull())
                    }
                }
            }
        }

        setContent {
            val currentGame by viewModel.currentGameFlow.collectAsState(initial = idle())
            GomokuTheme {
                GameScreen(
                    currentGame = currentGame,
                    selectedCell = viewModel.selectedCell,
                    onCellSelected = viewModel::changeSelectedCell,
                    currentUser = userInfoExtra.username,
                    onPlayRequested = viewModel::play,
                    onDismissError = viewModel::resetCurrentGameFlowFlowToIdle
                )
            }
        }
    }

    private fun doNavigation(game: Game?) {
        if (game != null && (game.board is BoardWin || game.board is BoardDraw)) {
            HomeActivity.navigateTo(origin = this, userInfoExtra)
            finish()
        }
    }
}