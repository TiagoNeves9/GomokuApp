package com.example.pdm2324i_gomoku_g37.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.UserInfoRepository
import com.example.pdm2324i_gomoku_g37.screens.main.MainScreenViewModel
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: UserInfoRepository) : ViewModel() {
    companion object {
        fun factory(repository: UserInfoRepository) = viewModelFactory {
            initializer { HomeScreenViewModel(repository) }
        }
    }

    private var _userInfo: UserInfo? by mutableStateOf(null)

    val userInfo: UserInfo?
        get() = _userInfo

    fun loadUserInfo() = viewModelScope.launch {
        _userInfo = repository.getUserInfo()
    }
}