package com.example.pdm2324i_gomoku_g37.screens.new_lobby

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.EnteringLobby
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.ReadyLobby
import com.example.pdm2324i_gomoku_g37.domain.LobbyAccessError
import com.example.pdm2324i_gomoku_g37.domain.LobbyScreenState
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.OutsideLobby
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Turn
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.domain.WaitingLobby
import com.example.pdm2324i_gomoku_g37.domain.board.BOARD_DIM
import com.example.pdm2324i_gomoku_g37.domain.toGameDto
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val POLLING_INTERVAL_VALUE = 5000L

class NewLobbyScreenViewModel(
    private val service: GomokuService,
    private val userInfo: UserInfo
) : ViewModel() {

    companion object {
        fun factory(service: GomokuService, userInfo: UserInfo) = viewModelFactory {
            initializer { NewLobbyScreenViewModel(service, userInfo) }
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

    private val _screenStateFlow: MutableStateFlow<LobbyScreenState> = MutableStateFlow(OutsideLobby)

    val screenState: Flow<LobbyScreenState>
        get() = _screenStateFlow.asStateFlow()

    fun resetToIdle() {
        _screenStateFlow.value = OutsideLobby
    }

    fun createLobbyAndWaitForPlayer() {
        check(_screenStateFlow.value is OutsideLobby) { "Cannot enter lobby twice" }

        val rules = Rules(_selectedBoardSize, _selectedGameOpening, _selectedGameVariant)

        viewModelScope.launch {
            try {
                service.createLobby(userInfo.token, rules).collect { newLobby ->
                    _screenStateFlow.value = EnteringLobby
                    while (_screenStateFlow.value !is ReadyLobby) {
                        val lobbyInfo = service.lobbyInfo(userInfo.token, newLobby.lobbyId)
                        if (lobbyInfo.guestUserId != null) {
                            val guestUserInfo: User = service.fetchUser(userInfo.token, lobbyInfo.guestUserId)
                            val hostPlayer: Player = Player(User(userInfo.id, userInfo.username), Turn.BLACK_PIECE)
                            val guestPlayer: Player = Player(guestUserInfo, Turn.WHITE_PIECE)
                            val newGame: Game = service.createGame(userInfo.token, newLobby.lobbyId, hostPlayer.first, guestPlayer.first)
                            _screenStateFlow.value = ReadyLobby(newGame)
                        }
                        delay(POLLING_INTERVAL_VALUE)
                    }
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
        check(_screenStateFlow.value is WaitingLobby) { "Cannot leave lobby" }

        val lobby = _screenStateFlow.value as WaitingLobby

        _screenStateFlow.value = OutsideLobby
        viewModelScope.launch {
            kotlin.runCatching { service.leaveLobby(userInfo.token, lobby.lobbyId) }
        }
    }
}