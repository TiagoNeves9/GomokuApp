package com.example.pdm2324i_gomoku_g37.screens.authors

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.service.FakeGomokuService


class AuthorsActivity : ComponentActivity() {

    private val viewModel by viewModels<AuthorsScreenViewModel>()
    private val services = FakeGomokuService()

    companion object {
        fun navigateTo(origin: HomeActivity) {
            val intent = Intent(origin, AuthorsActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthorsScreen(
                authors = viewModel.authors
            )
        }
    }

    override fun onStart() {
        viewModel.fetchAuthors(services)
        super.onStart()
    }
}