package com.example.pdm2324i_gomoku_g37.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.UserStatistics
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

    fun resetUserProfileToIdle() {
        _userProfileFlow.value = idle()
    }

    private val _userStatisticFlow: MutableStateFlow<LoadState<UserStatistics?>> = MutableStateFlow(idle())

    val userStatisticFlow: Flow<LoadState<UserStatistics?>>
        get() = _userStatisticFlow.asStateFlow()

    fun resetUserStatisticToIdle() {
        _userStatisticFlow.value = idle()
    }

    fun fetchUser() {
        _userProfileFlow.value = loading()
        viewModelScope.launch {
            val result: Result<User> = runCatching {
                service.fetchUserAccount(userInfo.id)
            }
            _userProfileFlow.value = loaded(result)
        }
    }

    fun fetchUserStatistic() {
        _userStatisticFlow.value = loading()
        viewModelScope.launch {
            val result: Result<UserStatistics> = runCatching {
                service.fetchRankings().first { it.user == userInfo.username }
            }
            _userStatisticFlow.value = loaded(result)
        }
    }
}