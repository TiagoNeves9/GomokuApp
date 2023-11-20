package com.example.pdm2324i_gomoku_g37.screens.home

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.rememberCoroutineScope
import com.example.pdm2324i_gomoku_g37.GomokuDependenciesContainer
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.screens.authors.AuthorsActivity
import com.example.pdm2324i_gomoku_g37.screens.main.MainScreenViewModel
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.lang.IllegalStateException


class HomeActivity : ComponentActivity() {

    private val dependencies by lazy { application as GomokuDependenciesContainer }

    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModel.factory(dependencies.userInfoRepository)
    }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            with(origin) {
                val intent = Intent(origin, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        viewModel.loadUserInfo()
        super.onStart()
    }

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContent {
            HomeScreen(
                userInfo = viewModel.userInfo ?: throw IllegalStateException(),
                onAuthorsRequested = { AuthorsActivity.navigateTo(origin = this) }
            )
        }
    }
}