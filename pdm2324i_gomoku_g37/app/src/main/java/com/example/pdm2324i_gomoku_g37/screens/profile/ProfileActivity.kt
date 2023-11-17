package com.example.pdm2324i_gomoku_g37.screens.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.pdm2324i_gomoku_g37.service.FakeGomokuService
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme

class ProfileActivity : ComponentActivity() {
    private val viewModel by viewModels<ProfileScreenViewModel>()
    private val services = FakeGomokuService()

    companion object{
        fun navigateTo(origin: ComponentActivity){
            val intent = Intent(origin, ProfileActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onStart() {
        viewModel.fetchProfile(services)
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            GomokuTheme {
                ProfileScreen()
            }
        }
    }
}