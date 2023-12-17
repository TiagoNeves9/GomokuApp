package com.example.pdm2324i_gomoku_g37.screens.play

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.pdm2324i_gomoku_g37.GomokuDependenciesContainer
import com.example.pdm2324i_gomoku_g37.domain.LobbyScreenState
import com.example.pdm2324i_gomoku_g37.domain.OutsideLobby
import com.example.pdm2324i_gomoku_g37.domain.ReadyLobby
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.toGameDto
import com.example.pdm2324i_gomoku_g37.screens.common.USER_INFO_EXTRA
import com.example.pdm2324i_gomoku_g37.screens.common.UserInfoExtra
import com.example.pdm2324i_gomoku_g37.screens.common.getUserInfoExtra
import com.example.pdm2324i_gomoku_g37.screens.common.toUserInfo
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.game.GameActivity
import com.example.pdm2324i_gomoku_g37.screens.info.InfoActivity
import com.example.pdm2324i_gomoku_g37.screens.new_lobby.NewLobbyActivity
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import kotlinx.coroutines.launch


class PlayActivity : ComponentActivity() {

    private val dependencies by lazy { application as GomokuDependenciesContainer }

    private val viewModel by viewModels<PlayScreenViewModel> {
        PlayScreenViewModel.factory(dependencies.gomokuService, userInfoExtra)
    }

    companion object {
        fun navigateTo(origin: Context, userInfo: UserInfo) {
            origin.startActivity(createIntent(origin, userInfo))
        }

        private fun createIntent(ctx: Context, userInfo: UserInfo): Intent {
            val intent = Intent(ctx, PlayActivity::class.java)
            intent.putExtra(USER_INFO_EXTRA, UserInfoExtra(userInfo))
            return intent
        }
    }

    /**
     * Helper method to get the user info extra from the intent.
     */
    private val userInfoExtra: UserInfo by lazy {
        checkNotNull(getUserInfoExtra(intent)).toUserInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetchLobbies()

                viewModel.screenState.collect {
                    if (it is ReadyLobby) {
                        Log.v("test_game_ready", it.toString())
                        GameActivity.navigateTo(this@PlayActivity, userInfoExtra, it.game.toGameDto())
                        finish()
                    }
                }
            }
        }

        setContent {
            val currentLobbies by viewModel.lobbiesFlow.collectAsState(initial = idle())
            val currentScreenState: LobbyScreenState = viewModel.screenState.collectAsState(OutsideLobby).value
            GomokuTheme {
                PlayScreen(
                    lobbies = currentLobbies,
                    lobbyScreenState = currentScreenState,
                    navigation = NavigationHandlers(
                        onBackRequested = { finish() },
                        onInfoRequested = {
                            InfoActivity.navigateTo(
                                origin = this,
                                userInfo = userInfoExtra
                            )
                        }
                    ),
                    onJoinRequested = viewModel::enterLobby,
                    onCreateRequested = { NewLobbyActivity.navigateTo(origin = this, userInfo = userInfoExtra) },
                    onDismissLobby = viewModel::leaveLobby
                )
            }
        }
    }
}