package com.example.pdm2324i_gomoku_g37.screens.play

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.Idle
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.LobbyAccessError
import com.example.pdm2324i_gomoku_g37.domain.LobbyScreenState
import com.example.pdm2324i_gomoku_g37.domain.OutsideLobby
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.WaitingLobby
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.loaded
import com.example.pdm2324i_gomoku_g37.domain.loading
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayScreenViewModel(
    private val service: GomokuService,
    private val userInfo: UserInfo
) : ViewModel() {

    companion object {
        fun factory(service: GomokuService, userInfo: UserInfo) = viewModelFactory {
            initializer { PlayScreenViewModel(service, userInfo) }
        }
    }

    private val _lobbiesFlow: MutableStateFlow<LoadState<List<WaitingLobby>?>> = MutableStateFlow(idle())

    val lobbiesFlow: Flow<LoadState<List<WaitingLobby>?>>
        get() = _lobbiesFlow.asStateFlow()

    fun resetLobbiesFlowToIdle() {
        _lobbiesFlow.value = idle()
    }

    fun fetchLobbies() {
        _lobbiesFlow.value = loading()
        viewModelScope.launch {
            val result: Result<List<WaitingLobby>> = runCatching {
                service.fetchLobbies()
            }
            _lobbiesFlow.value = loaded(result)
        }
    }

    private val _screenStateFlow: MutableStateFlow<LobbyScreenState> = MutableStateFlow(OutsideLobby)

    val screenState: Flow<LobbyScreenState>
        get() = _screenStateFlow.asStateFlow()

    fun resetScreenStateToIdle() {
        _screenStateFlow.value = OutsideLobby
    }

    fun enterLobby(lobby: WaitingLobby) {
        check(_screenStateFlow.value is OutsideLobby) { "Cannot enter lobby twice" }

        _screenStateFlow.value = WaitingLobby(lobby.lobbyId, lobby.hostUserId, lobby.guestUserId, lobby.rules)

        viewModelScope.launch {
            try {
                service.enterLobby(userInfo.token, lobby.lobbyId).collect { readyLobby ->
                    Log.v("vm_enter_lobby", readyLobby.toString())
                    _screenStateFlow.value = readyLobby
                }
            } catch (cause: Throwable) {
                if (_screenStateFlow.value is WaitingLobby) {
                    leaveLobby()
                }
                _screenStateFlow.value = LobbyAccessError(cause)
            }
        }
    }

    fun leaveLobby() {
        check(_screenStateFlow.value !is OutsideLobby) { "you must be in a lobby first" }
        check(_screenStateFlow.value is WaitingLobby) { "Cannot leave lobby" }

        val lobby = _screenStateFlow.value as WaitingLobby

        _screenStateFlow.value = OutsideLobby
        viewModelScope.launch {
            kotlin.runCatching { service.leaveLobby(userInfo.token, lobby.lobbyId) }
        }
    }
}