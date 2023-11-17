package com.example.pdm2324i_gomoku_g37.screens.play

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdm2324i_gomoku_g37.domain.Lobby
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.launch

class PlayScreenViewModel : ViewModel() {
    var lobbies: List<Lobby>? by mutableStateOf(null)
        private set

    fun fetchLobbies(services: GomokuService) =
        viewModelScope.launch {
            lobbies = services.fetchLobbies()
        }
}