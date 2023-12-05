package com.example.pdm2324i_gomoku_g37.screens.new_lobby

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.Idle
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.Lobby
import com.example.pdm2324i_gomoku_g37.domain.LobbyId
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.UserInfoRepository
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.domain.board.BOARD_DIM
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.loaded
import com.example.pdm2324i_gomoku_g37.domain.loading
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewLobbyScreenViewModel(
    private val service: GomokuService,
    private val repository: UserInfoRepository
) : ViewModel() {

    companion object {
        fun factory(service: GomokuService, repository: UserInfoRepository) = viewModelFactory {
            initializer { NewLobbyScreenViewModel(service, repository) }
        }
    }

    private var _selectedBoardSize by mutableIntStateOf(BOARD_DIM)

    val selectedBoardSize: Int
        get() = _selectedBoardSize

    fun changeSelectedBoardSize(boardSize: Int) = viewModelScope.launch {
        _selectedBoardSize = boardSize
    }

    private var _isBoardSizeInputExpanded by mutableStateOf(false)

    val isBoardSizeInputExpanded: Boolean
        get() = _isBoardSizeInputExpanded

    fun changeIsBoardSizeInputExpanded() = viewModelScope.launch {
        _isBoardSizeInputExpanded = !_isBoardSizeInputExpanded
    }

    private var _selectedGameOpening by mutableStateOf(Opening.FREESTYLE)

    val selectedGameOpening: Opening
        get() = _selectedGameOpening

    fun changeSelectedGameOpening(opening: Opening) = viewModelScope.launch {
        _selectedGameOpening = opening
    }

    private var _isGameOpeningInputExpanded by mutableStateOf(false)

    val isGameOpeningInputExpanded: Boolean
        get() = _isGameOpeningInputExpanded

    fun changeIsGameOpeningInputExpanded() = viewModelScope.launch {
        _isGameOpeningInputExpanded = !_isGameOpeningInputExpanded
    }

    private var _selectedGameVariant by mutableStateOf(Variant.FREESTYLE)

    val selectedGameVariant: Variant
        get() = _selectedGameVariant

    fun changeSelectedGameVariant(variant: Variant) = viewModelScope.launch {
        _selectedGameVariant = variant
    }

    private var _isGameVariantInputExpanded by mutableStateOf(false)

    val isGameVariantInputExpanded: Boolean
        get() = _isGameVariantInputExpanded

    fun changeIsGameVariantInputExpanded() = viewModelScope.launch {
        _isGameVariantInputExpanded = !_isGameVariantInputExpanded
    }

    private val _newLobbyFlow: MutableStateFlow<LoadState<Lobby?>> = MutableStateFlow(idle())

    val newLobbyFlow: Flow<LoadState<Lobby?>>
        get() = _newLobbyFlow.asStateFlow()

    fun resetToIdle() {
        _newLobbyFlow.value = idle()
    }

    fun createNewGame() {
        if (_newLobbyFlow.value !is Idle)
            throw IllegalStateException("The view model is not in the idle state.")
        _newLobbyFlow.value = loading()
        viewModelScope.launch {
            val result = kotlin.runCatching {
                val userInfo: UserInfo = repository.getUserInfo()
                    ?: throw IllegalStateException("The userInfo cannot be null at this stage")

                val rules = Rules(_selectedBoardSize, _selectedGameOpening, _selectedGameVariant)

                val lobbyId: LobbyId = service.createLobby(userInfo.token, rules)

                Lobby(lobbyId.id, userInfo.id, null, rules)
            }
            _newLobbyFlow.value = loaded(result)
        }
    }

}