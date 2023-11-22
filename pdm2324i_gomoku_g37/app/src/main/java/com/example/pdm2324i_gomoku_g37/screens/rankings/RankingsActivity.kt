package com.example.pdm2324i_gomoku_g37.screens.rankings

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.service.FakeGomokuService
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme

class RankingsActivity : ComponentActivity(){
    private val viewModel by viewModels<RankingsScreenViewModel>()
    private val services = FakeGomokuService()

    companion object{

        fun navigateTo(origin: ComponentActivity){
            val intent = Intent(origin, RankingsActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onStart() {
        viewModel.fetchRankings(services)
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme {
                RankingsScreen(
                    viewModel.rankings,
                    navigation = NavigationHandlers(
                        onBackRequested = { HomeActivity.navigateTo(origin = this)}
                    )
                )
            }
        }
    }


}