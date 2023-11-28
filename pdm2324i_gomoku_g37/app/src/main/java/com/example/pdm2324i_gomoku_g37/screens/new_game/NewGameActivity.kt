package com.example.pdm2324i_gomoku_g37.screens.new_game

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.pdm2324i_gomoku_g37.GomokuDependenciesContainer
import com.example.pdm2324i_gomoku_g37.screens.main.MainActivity
import com.example.pdm2324i_gomoku_g37.screens.signup.SignUpScreenViewModel
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme

class NewGameActivity : ComponentActivity() {
    /**
     * The application's dependency provider.
     */
    private val dependencies by lazy { application as GomokuDependenciesContainer }

    /**
     * The view model for the new game screen of the Gomoku app.
     */
    private val viewModel by viewModels<NewGameScreenViewModel> {
        NewGameScreenViewModel.factory(dependencies.gomokuService, dependencies.userInfoRepository)
    }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, NewGameActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme {
                NewGameScreen(
                    state = NewGameScreenState(
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
                        createNewGameRequested = viewModel::createNewGame
                    )
                )
            }
        }
    }
}