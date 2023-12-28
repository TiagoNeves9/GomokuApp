package isel.pdm.pdm2324i_gomoku_g37.screens.new_lobby

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import isel.pdm.pdm2324i_gomoku_g37.domain.EnteringLobby
import isel.pdm.pdm2324i_gomoku_g37.domain.LobbyAccessError
import isel.pdm.pdm2324i_gomoku_g37.domain.LobbyScreenState
import isel.pdm.pdm2324i_gomoku_g37.domain.Opening
import isel.pdm.pdm2324i_gomoku_g37.domain.OutsideLobby
import isel.pdm.pdm2324i_gomoku_g37.domain.ReadyLobby
import isel.pdm.pdm2324i_gomoku_g37.domain.Rules
import isel.pdm.pdm2324i_gomoku_g37.domain.UserInfo
import isel.pdm.pdm2324i_gomoku_g37.domain.Variant
import isel.pdm.pdm2324i_gomoku_g37.domain.WaitingLobby
import isel.pdm.pdm2324i_gomoku_g37.domain.board.BOARD_DIM
import isel.pdm.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


private const val POLLING_INTERVAL_VALUE = 5000L
private const val GAME_FLAG_CREATED = "CREATED"

class NewLobbyScreenViewModel(
    private val service: GomokuService,
    private val userInfo: UserInfo
) : ViewModel() {
    companion object {
        fun factory(service: GomokuService, userInfo: UserInfo) = viewModelFactory {
            initializer { NewLobbyScreenViewModel(service, userInfo) }
        }
    }

    private val scope = CoroutineScope(Dispatchers.IO)

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

    private val _screenStateFlow: MutableStateFlow<LobbyScreenState> =
        MutableStateFlow(OutsideLobby)

    val screenState: Flow<LobbyScreenState>
        get() = _screenStateFlow.asStateFlow()

    fun resetToIdle() {
        _screenStateFlow.value = OutsideLobby
    }

    fun createLobbyAndWaitForPlayer() {
        if (_screenStateFlow.value is OutsideLobby) {
            _screenStateFlow.value = EnteringLobby

            val rules = Rules(_selectedBoardSize, _selectedGameOpening, _selectedGameVariant)

            scope.launch {
                try {
                    service.createLobby(userInfo.token, rules).collect { newLobby ->
                        _screenStateFlow.value = WaitingLobby(
                            newLobby.lobbyId, newLobby.hostUserId, newLobby.boardDim,
                            newLobby.opening, newLobby.variant
                        )
                        while (_screenStateFlow.value !is ReadyLobby) {
                            val isGameCreated =
                                service.isGameCreated(userInfo.token, newLobby.lobbyId)
                            if (isGameCreated == GAME_FLAG_CREATED) {
                                val newGame =
                                    service.getGameById(userInfo.token, newLobby.lobbyId)
                                _screenStateFlow.value = ReadyLobby(newGame)
                            }
                            delay(POLLING_INTERVAL_VALUE)
                        }
                    }
                } catch (cause: Throwable) {
                    if (_screenStateFlow.value is WaitingLobby)
                        leaveLobby()
                    _screenStateFlow.value = LobbyAccessError(cause)
                }
            }
        }
    }

    fun leaveLobby() {
        check(_screenStateFlow.value is WaitingLobby) { "Must be in a lobby first" }

        val lobby = _screenStateFlow.value as WaitingLobby

        _screenStateFlow.value = OutsideLobby
        viewModelScope.launch {
            kotlin.runCatching { service.leaveLobby(userInfo.token, lobby.lobbyId) }
        }
    }
}