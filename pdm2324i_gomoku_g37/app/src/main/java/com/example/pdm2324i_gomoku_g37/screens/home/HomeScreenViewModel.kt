package com.example.pdm2324i_gomoku_g37.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.Idle
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.UserInfoRepository
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.loaded
import com.example.pdm2324i_gomoku_g37.domain.loading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: UserInfoRepository) : ViewModel() {

    private val _userInfoFlow: MutableStateFlow<LoadState<UserInfo?>> = MutableStateFlow(idle())

    val userInfoFlow: Flow<LoadState<UserInfo?>>
        get() = _userInfoFlow.asStateFlow()

    fun fetchUserInfo() {
        if (_userInfoFlow.value is Idle) {
            _userInfoFlow.value = loading()
            viewModelScope.launch {
                val result = runCatching { repository.getUserInfo() }
                _userInfoFlow.value = loaded(result)
            }
        }
    }

    fun resetToIdle() {
        _userInfoFlow.value = idle()
    }

    companion object {
        fun factory(repository: UserInfoRepository) = viewModelFactory {
            initializer { HomeScreenViewModel(repository) }
        }
    }
}