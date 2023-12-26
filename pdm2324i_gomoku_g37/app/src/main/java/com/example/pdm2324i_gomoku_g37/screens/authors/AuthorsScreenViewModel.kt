package com.example.pdm2324i_gomoku_g37.screens.authors

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.getOrNull
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.loaded
import com.example.pdm2324i_gomoku_g37.domain.loading
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthorsScreenViewModel(private val service: GomokuService) : ViewModel() {
    companion object {
        fun factory(service: GomokuService) = viewModelFactory {
            initializer { AuthorsScreenViewModel(service) }
        }
    }

    private val _authorsListFlow: MutableStateFlow<LoadState<List<Author>?>> =
        MutableStateFlow(idle())

    val authorsList: Flow<LoadState<List<Author>?>>
        get() = _authorsListFlow.asStateFlow()

    private var _index: Int by mutableIntStateOf(0)

    val index: Int
        get() = _index

    fun nextIndex() = viewModelScope.launch {
        _authorsListFlow.value.getOrNull()?.let { authors ->
            _index = if (_index == authors.size - 1) 0 else _index + 1
        }
    }

    fun prevIndex() = viewModelScope.launch {
        _authorsListFlow.value.getOrNull()?.let { authors ->
            _index = if (_index == 0) authors.size - 1 else _index - 1
        }
    }

    fun fetchAuthors() {
        _authorsListFlow.value = loading()
        viewModelScope.launch {
            val result = runCatching { service.fetchAuthors() }
            _authorsListFlow.value = loaded(result)
        }
    }
}