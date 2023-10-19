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

    var authors: List<Author> by mutableStateOf(emptyList())
        private set


    fun fetchAuthors(services : FakeGomokuService){
        viewModelScope.launch {
            authors = services.fetchAuthors()
        }
    }

}