package com.example.pdm2324i_gomoku_g37.Home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm2324i_gomoku_g37.Authors.AuthorsActivity
import com.example.pdm2324i_gomoku_g37.MainActivity

class HomeActivity : ComponentActivity(){

    companion object{
        fun navigateTo(origin : MainActivity): Unit{
            val intent = Intent(origin, HomeActivity::class.java)
            origin.startActivity(intent)
        }

    }
    override fun onCreate(savedInstance : Bundle? ){
        super.onCreate(savedInstance)
        setContent {
            HomeScreen(
                onAuthorsRequest = { }
            )
        }
    }

}