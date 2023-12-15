package com.example.pdm2324i_gomoku_g37.screens.rankings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.Idle
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.loaded
import com.example.pdm2324i_gomoku_g37.domain.loading
import com.example.pdm2324i_gomoku_g37.service.GomokuRankings
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class RankingsScreenViewModel(private val service: GomokuService) : ViewModel() {
    companion object {
        fun factory(service: GomokuService) = viewModelFactory {
            initializer { RankingsScreenViewModel(service) }
        }
    }

    private val _rankingsFlow: MutableStateFlow<LoadState<GomokuRankings.Rankings>> =
        MutableStateFlow(idle())
    val rankings: Flow<LoadState<GomokuRankings.Rankings>>
        get() = _rankingsFlow.asStateFlow()


    fun fetchRankings() {
        if (_rankingsFlow.value is Idle) {
            _rankingsFlow.value = loading()
            viewModelScope.launch {
                val result = runCatching { service.fetchRankings() }
                _rankingsFlow.value = loaded(result)
            }
        }
    }
}