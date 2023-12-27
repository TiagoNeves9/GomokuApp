package com.example.pdm2324i_gomoku_g37.screens.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.loaded
import com.example.pdm2324i_gomoku_g37.domain.loading
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InfoScreenViewModel(private val service: GomokuService) : ViewModel() {

    companion object {
        fun factory(service: GomokuService) = viewModelFactory {
            initializer { InfoScreenViewModel(service) }
        }
    }

    private val _apiInfoFlow: MutableStateFlow<LoadState<String?>> = MutableStateFlow(idle())

    val apiInfoFlow: Flow<LoadState<String?>>
        get() = _apiInfoFlow.asStateFlow()

    fun resetToIdle() {
        _apiInfoFlow.value = idle()
    }

    fun fetchInfo() {
        _apiInfoFlow.value = loading()
        viewModelScope.launch {
            val result: Result<String> = runCatching { service.fetchApiInfo() }
            _apiInfoFlow.value = loaded(result)
        }
    }
}