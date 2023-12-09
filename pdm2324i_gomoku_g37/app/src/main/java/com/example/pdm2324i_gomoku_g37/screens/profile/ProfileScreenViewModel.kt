package com.example.pdm2324i_gomoku_g37.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.Idle
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.loaded
import com.example.pdm2324i_gomoku_g37.domain.loading
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProfileScreenViewModel(
    private val service: GomokuService,
    private val userInfo: UserInfo
) : ViewModel() {

    companion object {
        fun factory(service: GomokuService, userInfo: UserInfo) = viewModelFactory {
            initializer { ProfileScreenViewModel(service, userInfo) }
        }
    }

    private val _userProfileFlow: MutableStateFlow<LoadState<User?>> = MutableStateFlow(idle())

    val userProfileFlow: Flow<LoadState<User?>>
        get() = _userProfileFlow.asStateFlow()

    fun resetToIdle() {
        _userProfileFlow.value = idle()
    }


    fun fetchUser() {
        if (_userProfileFlow.value is Idle) {
            _userProfileFlow.value = loading()
            viewModelScope.launch {
                val result: Result<User> = runCatching {
                    service.fetchUser(userInfo.token, userInfo.id)
                }
                _userProfileFlow.value = loaded(result)
            }
        }
    }
}