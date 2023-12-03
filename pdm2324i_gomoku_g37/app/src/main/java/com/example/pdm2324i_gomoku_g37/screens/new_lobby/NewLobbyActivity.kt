package com.example.pdm2324i_gomoku_g37.screens.new_lobby

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.example.pdm2324i_gomoku_g37.GomokuDependenciesContainer
import com.example.pdm2324i_gomoku_g37.domain.Loaded
import com.example.pdm2324i_gomoku_g37.domain.Lobby
import com.example.pdm2324i_gomoku_g37.domain.getOrNull
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.screens.lobby.LobbyActivity
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import kotlinx.coroutines.launch

class NewLobbyActivity : ComponentActivity() {
    /**
     * The application's dependency provider.
     */
    private val dependencies by lazy { application as GomokuDependenciesContainer }

    /**
     * The view model for the new game screen of the Gomoku app.
     */
    private val viewModel by viewModels<NewLobbyScreenViewModel> {
        NewLobbyScreenViewModel.factory(dependencies.gomokuService, dependencies.userInfoRepository)
    }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, NewLobbyActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.newLobbyFlow.collect {
                if (it is Loaded) {
                    doNavigation(it.getOrNull())
                }
            }
        }

        setContent {
            val currentNewLobby by viewModel.newLobbyFlow.collectAsState(initial = idle())
            GomokuTheme {
                NewLobbyScreen(
                    state = NewGameScreenState(
                        lobby = currentNewLobby,
                        selectedBoardSize = viewModel.selectedBoardSize,
                        isBoardSizeInputExpanded = viewModel.isBoardSizeInputExpanded,
                        selectedGameOpening = viewModel.selectedGameOpening,
                        isGameOpeningInputExpanded = viewModel.isGameOpeningInputExpanded,
                        selectedGameVariant = viewModel.selectedGameVariant,
                        isGameVariantInputExpanded = viewModel.isGameVariantInputExpanded
                    ),
                    functions = NewGameScreenFunctions(
                        changeSelectedBoardSize = viewModel::changeSelectedBoardSize,
                        changeIsBoardSizeInputExpanded = viewModel::changeIsBoardSizeInputExpanded,
                        changeSelectedGameOpening = viewModel::changeSelectedGameOpening,
                        changeIsGameOpeningInputExpanded = viewModel::changeIsGameOpeningInputExpanded,
                        changeSelectedGameVariant = viewModel::changeSelectedGameVariant,
                        changeIsGameVariantInputExpanded = viewModel::changeIsGameVariantInputExpanded,
                        createNewGameRequested = viewModel::createNewGame,
                        onDismissError = viewModel::resetToIdle
                    )
                )
            }
        }
    }

    private fun doNavigation(lobby: Lobby?) {
        if (lobby != null) {
            LobbyActivity.navigateTo(this, lobby)
            finish()
        }
    }
}