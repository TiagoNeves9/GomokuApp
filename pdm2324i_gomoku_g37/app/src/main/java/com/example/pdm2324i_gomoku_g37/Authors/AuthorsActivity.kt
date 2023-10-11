package com.example.pdm2324i_gomoku_g37.Authors

import android.content.Intent
import androidx.activity.ComponentActivity
import com.example.pdm2324i_gomoku_g37.Home.HomeActivity
import com.example.pdm2324i_gomoku_g37.MainActivity

class AuthorsActivity : ComponentActivity(){

    companion object{
        fun navigateTo(origin : MainActivity): Unit{
            val intent = Intent(origin, HomeActivity::class.java)
            origin.startActivity(intent)
        }

    }

}