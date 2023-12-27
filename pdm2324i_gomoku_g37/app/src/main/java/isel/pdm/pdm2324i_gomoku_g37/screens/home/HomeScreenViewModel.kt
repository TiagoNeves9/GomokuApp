package isel.pdm.pdm2324i_gomoku_g37.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import isel.pdm.pdm2324i_gomoku_g37.domain.UserInfoRepository
import kotlinx.coroutines.launch


class HomeScreenViewModel(private val repository: UserInfoRepository) : ViewModel() {
    companion object {
        fun factory(repository: UserInfoRepository) = viewModelFactory {
            initializer { HomeScreenViewModel(repository) }
        }
    }

    private var _error: Exception? by mutableStateOf(null)

    val error: Exception?
        get() = _error

    fun onDismissError() = viewModelScope.launch {
        _error = null
    }

    fun logout() = viewModelScope.launch {
        try {
            repository.clearUserInfo()
        } catch (e: Exception) {
            _error = e
        }
    }
}