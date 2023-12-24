package com.example.pdm2324i_gomoku_g37.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.WaitingLobby
import com.example.pdm2324i_gomoku_g37.domain.dtos.LocalGameInfoDto
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.loaded
import com.example.pdm2324i_gomoku_g37.domain.loading
import com.example.pdm2324i_gomoku_g37.screens.play.PlayScreenViewModel
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val POLLING_INTERVAL_VALUE = 5000L

class GameScreenViewModel(
    private val service: GomokuService,
    private val userInfo: UserInfo,
    private val gameInfo: LocalGameInfoDto
) : ViewModel() {
    companion object {
        fun factory(service: GomokuService, userInfo: UserInfo, gameInfo: LocalGameInfoDto) = viewModelFactory {
            initializer { GameScreenViewModel(service, userInfo, gameInfo) }
        }
    }

    private val _currentGameFlow: MutableStateFlow<LoadState<Game?>> = MutableStateFlow(
        idle()
    )

    val currentGameFlow: Flow<LoadState<Game?>>
        get() = _currentGameFlow.asStateFlow()

    fun resetCurrentGameFlowFlowToIdle() {
        _currentGameFlow.value = idle()
    }

    fun fetchGame() {
        _currentGameFlow.value = loading()
        viewModelScope.launch {
            while (true) {
                val result: Result<Game> = runCatching {
                    service.getGameById(userInfo.token, gameInfo.gameId)
                }
                _currentGameFlow.value = loaded(result)
                delay(POLLING_INTERVAL_VALUE)
            }
        }
    }
}