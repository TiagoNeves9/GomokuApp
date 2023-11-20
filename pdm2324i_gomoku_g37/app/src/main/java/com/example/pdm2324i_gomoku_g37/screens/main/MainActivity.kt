package com.example.pdm2324i_gomoku_g37.screens.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.rememberCoroutineScope
import com.example.pdm2324i_gomoku_g37.GomokuDependenciesContainer
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Turn
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.domain.board.BOARD_DIM
import com.example.pdm2324i_gomoku_g37.domain.board.createBoard
import com.example.pdm2324i_gomoku_g37.screens.authors.AuthorsScreenViewModel
import com.example.pdm2324i_gomoku_g37.screens.game.GameActivity
import com.example.pdm2324i_gomoku_g37.screens.game.GameScreen
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.screens.login.LoginActivity
import com.example.pdm2324i_gomoku_g37.screens.signup.SignUpActivity
import com.example.pdm2324i_gomoku_g37.screens.signup.SignUpScreenViewModel
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import kotlinx.coroutines.launch
import java.util.UUID


class MainActivity : ComponentActivity() {

    private val dependencies by lazy { application as GomokuDependenciesContainer }

    private val viewModel by viewModels<MainScreenViewModel> {
        MainScreenViewModel.factory(dependencies.userInfoRepository)
    }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, MainActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onStart() {
        viewModel.loadUserInfo()
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme {
                MainScreen(
                    onStartRequested = {
                        if (viewModel.userInfo != null) HomeActivity.navigateTo(origin = this@MainActivity)
                        else LoginActivity.navigateTo(origin = this@MainActivity)
                    }
                )
            }
        }
    }
}