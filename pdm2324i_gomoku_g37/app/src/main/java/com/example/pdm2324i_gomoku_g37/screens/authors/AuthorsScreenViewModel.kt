package com.example.pdm2324i_gomoku_g37.screens.authors

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.service.FakeGomokuService
import kotlinx.coroutines.launch

class AuthorsScreenViewModel : ViewModel(){

    var authors: List<Author>? by mutableStateOf(null)
        private set

    var index: Int by mutableStateOf(0)
        private set

    fun fetchAuthors(services : FakeGomokuService){
        viewModelScope.launch {
            authors = services.fetchAuthors()
        }
    }

    fun nextIndex() {
        viewModelScope.launch {
            authors?.let {
                index = if (index == it.size - 1) 0 else index + 1
            }
        }
    }

    fun prevIndex() {
        viewModelScope.launch {
            authors?.let {
                index = if (index == 0) it.size - 1 else index - 1
            }
        }
    }

}