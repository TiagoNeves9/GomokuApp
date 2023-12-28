package isel.pdm.pdm2324i_gomoku_g37.screens.rankings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import isel.pdm.pdm2324i_gomoku_g37.domain.LoadState
import isel.pdm.pdm2324i_gomoku_g37.domain.UserStatistics
import isel.pdm.pdm2324i_gomoku_g37.domain.idle
import isel.pdm.pdm2324i_gomoku_g37.domain.loaded
import isel.pdm.pdm2324i_gomoku_g37.domain.loading
import isel.pdm.pdm2324i_gomoku_g37.service.GomokuService
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

    private val _rankingsFlow: MutableStateFlow<LoadState<List<UserStatistics>>> =
        MutableStateFlow(idle())

    val rankings: Flow<LoadState<List<UserStatistics>>>
        get() = _rankingsFlow.asStateFlow()

    fun resetToIdle() {
        _rankingsFlow.value = idle()
    }

    fun fetchRankings() {
        _rankingsFlow.value = loading()
        viewModelScope.launch {
            val result = runCatching { service.fetchRankings() }
            _rankingsFlow.value = loaded(result)
        }
    }
}