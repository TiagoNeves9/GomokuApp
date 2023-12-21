package com.example.pdm2324i_gomoku_g37.domain.dtos

import android.os.Parcelable
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Turn
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.domain.board.Board
import com.example.pdm2324i_gomoku_g37.domain.board.BoardDraw
import com.example.pdm2324i_gomoku_g37.domain.board.Cell
import com.example.pdm2324i_gomoku_g37.domain.board.Column
import com.example.pdm2324i_gomoku_g37.domain.board.Row
import kotlinx.parcelize.Parcelize
import java.time.Instant
import java.util.UUID

@Parcelize
data class LocalGameInfoDto(
    val gameId: String,
    val user1: LocalUserDto,
    val user2: LocalUserDto,
    val board: LocalBoardDto,
    val currentPlayer: LocalPlayerDto,
    val score: Int,
    val now: Instant,
    val rules: LocalRulesDto
) : Parcelable

@Parcelize
data class LocalPlayerDto(val user: LocalUserDto, val turn: Turn) : Parcelable

@Parcelize
data class LocalUserDto(val id: String, val username: String, val encodedPassword: String) : Parcelable

@Parcelize
data class LocalBoardDto(val positions: Map<LocalCellDto, Turn>, val boardSize: Int) : Parcelable

@Parcelize
data class LocalCellDto(val row: LocalRowDto, val col: LocalColumnDto) : Parcelable

@Parcelize
data class LocalRowDto(val number: Int) : Parcelable

@Parcelize
data class LocalColumnDto(val symbol: Char) : Parcelable

@Parcelize
data class LocalRulesDto(val boardDim: Int, val opening: Opening, val variant: Variant) : Parcelable

fun LocalGameInfoDto.toGame(): Game = Game(
    gameId = gameId,
    users = Pair(user1.toUser(), user2.toUser()),
    board = board.toBoard(),
    currentPlayer = currentPlayer.toPlayer(),
    score = score,
    now = now,
    rules = rules.toRules()
)

fun LocalUserDto.toUser(): User = User(id, username, encodedPassword)

fun LocalBoardDto.toBoard(): Board = BoardDraw(
    positions.entries.associateTo(mutableMapOf()) { entry ->
        val (key, value) = entry
        Pair(Cell.invoke(Row.invoke(key.row.number), Column.invoke(key.col.symbol)), value)
    },
    boardSize
)

fun LocalPlayerDto.toPlayer(): Player = Pair(user.toUser(), turn)

fun LocalRulesDto.toRules(): Rules = Rules(boardDim, opening, variant)

fun Player.toPlayerDto(): LocalPlayerDto = LocalPlayerDto(
    first.toLocalUserDto(), second
)

fun Board.toBoardDto(): LocalBoardDto = LocalBoardDto(
    positions.entries.associateTo(mutableMapOf()) { entry ->
        val (key, value) = entry
        Pair(LocalCellDto(LocalRowDto(key.row.number), LocalColumnDto(key.col.symbol)), value)
    },
    boardSize
)

fun Rules.toRulesDto(): LocalRulesDto = LocalRulesDto(boardDim, opening, variant)

fun User.toLocalUserDto(): LocalUserDto = LocalUserDto(userId, username, encodedPassword)

