package com.example.pdm2324i_gomoku_g37.screens.rankings

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.example.pdm2324i_gomoku_g37.GomokuDependenciesContainer
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.service.FakeGomokuService
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import kotlinx.coroutines.launch

class RankingsActivity : ComponentActivity(){
    private val dependecies by lazy { application as GomokuDependenciesContainer }
    private val viewModel by viewModels<RankingsScreenViewModel>{
        RankingsScreenViewModel.factory(dependecies.gomokuService)
    }

    companion object{
        fun navigateTo(origin: ComponentActivity){
            val intent = Intent(origin, RankingsActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.fetchRankings()
        }

        setContent {
            val currentRankings by viewModel.rankings.collectAsState(initial = idle())
            GomokuTheme {
                RankingsScreen(
                    rankings = currentRankings,
                    navigation = NavigationHandlers(
                        onBackRequested = { HomeActivity.navigateTo(origin = this)}
                    )
                )
            }
        }
    }


}