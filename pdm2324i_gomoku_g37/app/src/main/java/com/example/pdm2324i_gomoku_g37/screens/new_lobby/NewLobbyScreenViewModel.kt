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
import com.example.pdm2324i_gomoku_g37.domain.Idle
import com.example.pdm2324i_gomoku_g37.domain.InsideLobby
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.Loaded
import com.example.pdm2324i_gomoku_g37.domain.Lobby
import com.example.pdm2324i_gomoku_g37.domain.LobbyAccessError
import com.example.pdm2324i_gomoku_g37.domain.LobbyId
import com.example.pdm2324i_gomoku_g37.domain.LobbyScreenState
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.OutsideLobby
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Turn
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.domain.board.BOARD_DIM
import com.example.pdm2324i_gomoku_g37.domain.getOrNull
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

    private val _newLobbyFlow: MutableStateFlow<LoadState<Lobby?>> = MutableStateFlow(idle())

    val newLobbyFlow: Flow<LoadState<Lobby?>>
        get() = _newLobbyFlow.asStateFlow()

    fun resetToIdle() {
        _newLobbyFlow.value = idle()
    }

    /**
     * new section
     */

    private val _newGameFlow: MutableStateFlow<LoadState<Game?>> = MutableStateFlow(idle())

    val newGameFlow: Flow<LoadState<Game?>>
        get() = _newGameFlow.asStateFlow()

    private val _screenStateFlow: MutableStateFlow<LobbyScreenState> = MutableStateFlow(OutsideLobby)

    val screenState: Flow<LobbyScreenState>
        get() = _screenStateFlow.asStateFlow()


    fun createLobbyAndWaitForPlayer() {
        check(_newLobbyFlow.value !is Idle) { "The view model is not in the idle state." }
        check(_screenStateFlow.value is OutsideLobby) { "Cannot enter lobby twice" }
        _newGameFlow.value = loading()
        _screenStateFlow.value = EnteringLobby

        val rules = Rules(_selectedBoardSize, _selectedGameOpening, _selectedGameVariant)

        viewModelScope.launch {
            service.createLobby(userInfo.token, rules).collect { newLobby ->
                while (_screenStateFlow.value !is InsideLobby) {
                    try {
                        val lobbyInfo = service.lobbyInfo(userInfo.token, newLobby.lobbyId)
                        if (lobbyInfo.guestUserId != null) {
                            val hostPlayer = Player(User(userInfo.id, userInfo.username), Turn.BLACK_PIECE)
                            val guestUserInfo = service.fetchUser(userInfo.token, lobbyInfo.guestUserId)
                            val guestPlayer = Player(guestUserInfo, Turn.WHITE_PIECE)
                            _screenStateFlow.value = InsideLobby(newLobby.lobbyId, hostPlayer, guestPlayer, newLobby.rules)
                            val newGame: Result<Game> = kotlin.runCatching {
                                service.createGame(userInfo.token, newLobby.lobbyId, hostPlayer.first, guestPlayer.first)
                            }
                            _newGameFlow.value = loaded(newGame)
                        }
                    } catch (cause: Throwable) {
                        _screenStateFlow.value = LobbyAccessError(cause)
                    }
                }
            }
        }
    }

    fun leaveLobby(lobbyId: String) {
        check(_screenStateFlow.value !is OutsideLobby) { "Cannot leave lobby twice" }
        _screenStateFlow.value = OutsideLobby
        viewModelScope.launch {
            kotlin.runCatching {
                if (_newLobbyFlow.value is Loaded) //use lobbyId from newLobby
                    service.leaveLobby(userInfo.token, lobbyId)
            }
        }
    }
}