package com.example.pdm2324i_gomoku_g37.screens.common

import android.content.Intent
import android.os.Parcelable
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.board.Board
import com.example.pdm2324i_gomoku_g37.domain.dtos.GameDto
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.time.Instant

const val GAME_EXTRA = "Game"

@Suppress("DEPRECATION")
fun getGameExtra(intent: Intent): GameDto? =
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
        intent.getParcelableExtra(GAME_EXTRA, GameDto::class.java)
    else
        intent.getParcelableExtra(GAME_EXTRA)