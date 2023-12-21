package com.example.pdm2324i_gomoku_g37.screens.common

import android.content.Intent
import com.example.pdm2324i_gomoku_g37.domain.dtos.LocalGameInfoDto

const val GAME_EXTRA = "Game"

@Suppress("DEPRECATION")
fun getGameExtra(intent: Intent): LocalGameInfoDto? =
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
        intent.getParcelableExtra(GAME_EXTRA, LocalGameInfoDto::class.java)
    else
        intent.getParcelableExtra(GAME_EXTRA)