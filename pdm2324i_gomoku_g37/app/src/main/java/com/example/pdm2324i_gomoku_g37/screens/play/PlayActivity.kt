package com.example.pdm2324i_gomoku_g37.screens.play

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.screens.info.InfoActivity
import com.example.pdm2324i_gomoku_g37.service.FakeGomokuService
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme

class PlayActivity : ComponentActivity() {
    private val viewModel by viewModels<PlayScreenViewModel>()
    private val services = FakeGomokuService()

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, PlayActivity::class.java)
            origin.startActivity(intent)
        }
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
                                userInfo = UserInfo(-1, "CHANGE_THIS")
                            )
                        }, //TODO change this hardcoded userInfo
                        onInfoRequested = { InfoActivity.navigateTo(origin = this) }
                    )
                )
            }
        }
    }
}