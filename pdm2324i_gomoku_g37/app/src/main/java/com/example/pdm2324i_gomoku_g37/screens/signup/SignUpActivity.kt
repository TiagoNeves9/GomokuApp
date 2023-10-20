package com.example.pdm2324i_gomoku_g37.screens.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm2324i_gomoku_g37.screens.home.HomeActivity
import com.example.pdm2324i_gomoku_g37.screens.login.LoginActivity
import com.example.pdm2324i_gomoku_g37.screens.login.LoginScreen
import com.example.pdm2324i_gomoku_g37.screens.main.MainActivity

class SignUpActivity : ComponentActivity() {

    companion object{
        fun navigateTo(origin: MainActivity) {
            val intent = Intent(origin, SignUpActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContent {
            SignUpScreen(
                onBackRequested = {
                    MainActivity.navigateTo(origin = this)
                },
                onHomeRequested = {
                    HomeActivity.navigateTo(origin = this)
                }
            )
        }
    }
}