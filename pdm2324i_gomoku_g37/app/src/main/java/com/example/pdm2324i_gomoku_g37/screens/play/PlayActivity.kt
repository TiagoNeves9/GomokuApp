package com.example.pdm2324i_gomoku_g37.screens.play

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.screens.common.USER_INFO_EXTRA
import com.example.pdm2324i_gomoku_g37.screens.common.UserInfoExtra
import com.example.pdm2324i_gomoku_g37.screens.common.getUserInfoExtra
import com.example.pdm2324i_gomoku_g37.screens.common.toUserInfo
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.screens.info.InfoActivity
import com.example.pdm2324i_gomoku_g37.screens.new_lobby.NewLobbyActivity
import com.example.pdm2324i_gomoku_g37.service.FakeGomokuService
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme

class PlayActivity : ComponentActivity() {
    private val viewModel by viewModels<PlayScreenViewModel>()
    private val services = FakeGomokuService()

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

    override fun onStart() {
        viewModel.fetchLobbies(services)
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme {
                PlayScreen(
                    lobbies = viewModel.lobbies,
                    navigation = NavigationHandlers(
                        onBackRequested = {
                            HomeActivity.navigateTo(
                                origin = this,
                                userInfo = userInfoExtra
                            )
                        },
                        onInfoRequested = {
                            InfoActivity.navigateTo(
                                origin = this,
                                userInfo = userInfoExtra
                            )
                        }
                    ),
                    onCreateRequested = {
                        NewLobbyActivity.navigateTo(
                            origin = this,
                            userInfo = userInfoExtra
                        )
                    }
                )
            }
        }
    }
}