package com.example.pdm2324i_gomoku_g37.screens.new_lobby

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.example.pdm2324i_gomoku_g37.GomokuDependenciesContainer
import com.example.pdm2324i_gomoku_g37.domain.LobbyScreenState
import com.example.pdm2324i_gomoku_g37.domain.OutsideLobby
import com.example.pdm2324i_gomoku_g37.domain.ReadyLobby
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.screens.common.USER_INFO_EXTRA
import com.example.pdm2324i_gomoku_g37.screens.common.UserInfoExtra
import com.example.pdm2324i_gomoku_g37.screens.common.getUserInfoExtra
import com.example.pdm2324i_gomoku_g37.screens.common.toUserInfo
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import kotlinx.coroutines.launch


class NewLobbyActivity : ComponentActivity() {
    companion object {
        fun navigateTo(origin: Context, userInfo: UserInfo) {
            origin.startActivity(createIntent(origin, userInfo))
        }

        private fun createIntent(ctx: Context, userInfo: UserInfo): Intent {
            val intent = Intent(ctx, NewLobbyActivity::class.java)
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

    /**
     * The application's dependency provider.
     */
    private val dependencies by lazy {
        application as GomokuDependenciesContainer
    }

    /**
     * The view model for the new game screen of the Gomoku app.
     */
    private val viewModel by viewModels<NewLobbyScreenViewModel> {
        NewLobbyScreenViewModel.factory(dependencies.gomokuService, userInfoExtra)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.screenState.collect {
                if (it is ReadyLobby) {
                    //GameActivity.navigateTo(it.game)
                    TODO()
                }
            }
        }

        setContent {
            val currentLobbyState: LobbyScreenState =
                viewModel.screenState.collectAsState(OutsideLobby).value
            GomokuTheme {
                NewLobbyScreen(
                    state = NewGameScreenState(
                        lobbyScreenState =
                        currentLobbyState,
                        selectedBoardSize =
                        viewModel.selectedBoardSize,
                        isBoardSizeInputExpanded =
                        viewModel.isBoardSizeInputExpanded,
                        selectedGameOpening =
                        viewModel.selectedGameOpening,
                        isGameOpeningInputExpanded =
                        viewModel.isGameOpeningInputExpanded,
                        selectedGameVariant =
                        viewModel.selectedGameVariant,
                        isGameVariantInputExpanded =
                        viewModel.isGameVariantInputExpanded
                    ),
                    functions = NewGameScreenFunctions(
                        changeSelectedBoardSize =
                        viewModel::changeSelectedBoardSize,
                        changeIsBoardSizeInputExpanded =
                        viewModel::changeIsBoardSizeInputExpanded,
                        changeSelectedGameOpening =
                        viewModel::changeSelectedGameOpening,
                        changeIsGameOpeningInputExpanded =
                        viewModel::changeIsGameOpeningInputExpanded,
                        changeSelectedGameVariant =
                        viewModel::changeSelectedGameVariant,
                        changeIsGameVariantInputExpanded =
                        viewModel::changeIsGameVariantInputExpanded,
                        createNewGameRequested =
                        viewModel::createLobbyAndWaitForPlayer,
                        onDismissError =
                        viewModel::resetToIdle
                    )
                )
            }
        }
    }
}