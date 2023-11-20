package com.example.pdm2324i_gomoku_g37.screens.authors

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.screens.signup.SignUpScreenViewModel
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.launch


class AuthorsScreenViewModel(private val service: GomokuService) : ViewModel() {

    companion object {
        fun factory(service: GomokuService) = viewModelFactory {
            initializer { AuthorsScreenViewModel(service) }
        }
    }

    private var _authors: List<Author>? by mutableStateOf(null)

    val authors: List<Author>?
        get() = _authors

    private var _index: Int by mutableStateOf(0)

    val index: Int
        get() = _index

    fun fetchAuthors() = viewModelScope.launch {
        _authors = service.fetchAuthors()
    }

    fun nextIndex() = viewModelScope.launch {
        authors?.let {
            _index = if (index == it.size - 1) 0 else index + 1
        }
    }

    fun prevIndex() = viewModelScope.launch {
        authors?.let {
            _index = if (index == 0) it.size - 1 else index - 1
        }
    }
}