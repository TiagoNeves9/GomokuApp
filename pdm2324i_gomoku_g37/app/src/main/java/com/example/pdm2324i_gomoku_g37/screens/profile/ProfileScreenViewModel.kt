package com.example.pdm2324i_gomoku_g37.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.launch


class ProfileScreenViewModel : ViewModel() {
    var profile: String? by mutableStateOf(null)
    fun fetchProfile(services: GomokuService) =
        viewModelScope.launch {
            profile = services.fetchProfile()
        }
}