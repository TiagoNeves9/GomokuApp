package com.example.pdm2324i_gomoku_g37.screens.rankings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.launch

class RankingsScreenViewModel : ViewModel(){
    var rankings : String? by mutableStateOf(null)
    private set

    fun fetchRankings(service: GomokuService) =
        viewModelScope.launch {
            rankings = service.fetchRankings()
        }
}