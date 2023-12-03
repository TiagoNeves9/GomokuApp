package com.example.pdm2324i_gomoku_g37.screens.lobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.Idle
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.UserInfoRepository
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.loading
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LobbyScreenViewModel(
    private val service: GomokuService,
    private val repository: UserInfoRepository
) : ViewModel() {
    companion object {
        fun factory(service: GomokuService, repository: UserInfoRepository) = viewModelFactory {
            initializer { LobbyScreenViewModel(service, repository) }
        }
    }

    private val _newGameFlow: MutableStateFlow<LoadState<Game?>> = MutableStateFlow(idle())

    val newGameFlow: Flow<LoadState<Game?>>
        get() = _newGameFlow.asStateFlow()

    fun resetToIdle() {
        _newGameFlow.value = idle()
    }

    fun waitForGame() {
        if (_newGameFlow.value !is Idle)
            throw IllegalStateException("The view model is not in the idle state.")

        _newGameFlow.value = loading()

        viewModelScope.launch {
            while (true) {
                val result = kotlin.runCatching {
                    //services.getGame()
                }
                delay(1000)
            }
        }
    }
}