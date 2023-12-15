package com.example.pdm2324i_gomoku_g37.domain.dtos

import android.os.Parcelable
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.Turn
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.Variant
import kotlinx.parcelize.Parcelize
import java.time.Instant

@Parcelize
data class GameDto(
    val gameId: String,
    val user1: PlayerDto,
    val user2: PlayerDto,
    val board: BoardDto,
    val currentPlayer: PlayerDto,
    val score: Int,
    val now: Instant,
    val rules: RulesDto
) : Parcelable

@Parcelize
data class PlayerDto(val user: UserDto, val turn: Turn) : Parcelable

@Parcelize
data class UserDto(val id: String, val username: String) : Parcelable

@Parcelize
data class BoardDto(val positions: Map<CellDto, Turn>, val boardSize: Int) : Parcelable

@Parcelize
data class CellDto(val row: RowDto, val col: ColumnDto) : Parcelable

@Parcelize
data class RowDto(val number: Int) : Parcelable

@Parcelize
data class ColumnDto(val symbol: Char) : Parcelable

@Parcelize
data class RulesDto(val boardDim: Int, val opening: Opening, val variant: Variant) : Parcelable


