package com.example.pdm2324i_gomoku_g37.screens.new_game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.UserInfoRepository
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.domain.board.BOARD_DIM
import com.example.pdm2324i_gomoku_g37.screens.signup.SignUpScreenViewModel
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.launch

class NewGameScreenViewModel(
    private val service: GomokuService,
    private val repository: UserInfoRepository
) : ViewModel() {

    companion object {
        fun factory(service: GomokuService, repository: UserInfoRepository) = viewModelFactory {
            initializer { NewGameScreenViewModel(service, repository) }
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

    fun createNewGame() {

    }

}