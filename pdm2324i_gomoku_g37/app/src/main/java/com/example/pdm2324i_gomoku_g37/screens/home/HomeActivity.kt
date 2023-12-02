package com.example.pdm2324i_gomoku_g37.screens.home

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
import com.example.pdm2324i_gomoku_g37.domain.getOrNull
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.screens.authors.AuthorsActivity
import com.example.pdm2324i_gomoku_g37.screens.info.InfoActivity
import com.example.pdm2324i_gomoku_g37.screens.login.LoginActivity
import com.example.pdm2324i_gomoku_g37.screens.play.PlayActivity
import com.example.pdm2324i_gomoku_g37.screens.rankings.RankingsActivity
import kotlinx.coroutines.launch


class HomeActivity : ComponentActivity() {
    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, HomeActivity::class.java)
            origin.startActivity(intent)
        }
    }

    private val dependencies by lazy { application as GomokuDependenciesContainer }

    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModel.factory(dependencies.userInfoRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.fetchUserInfo()
            viewModel.userInfoFlow.collect {
                if (it is Loaded && it.getOrNull() == null)
                    LoginActivity.navigateTo(this@HomeActivity)
            }
        }

        setContent {
            val currentUserInfo by viewModel.userInfoFlow.collectAsState(
                initial = idle()
            )
            HomeScreen(
                userInfo = currentUserInfo,
                onAuthorsRequested = { AuthorsActivity.navigateTo(origin = this) },
                onPlayRequested = { PlayActivity.navigateTo(origin = this) },
                onRankingsRequested = { RankingsActivity.navigateTo(origin = this) },
                onAboutRequested = { InfoActivity.navigateTo(origin = this) },
                onDismissError = viewModel::resetToIdle
            )
        }
    }
}