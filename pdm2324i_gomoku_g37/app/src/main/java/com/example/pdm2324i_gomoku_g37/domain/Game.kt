package com.example.pdm2324i_gomoku_g37.domain

import com.example.pdm2324i_gomoku_g37.domain.board.Board
import com.example.pdm2324i_gomoku_g37.domain.board.BoardDraw
import com.example.pdm2324i_gomoku_g37.domain.board.BoardRun
import com.example.pdm2324i_gomoku_g37.domain.board.Cell
import com.example.pdm2324i_gomoku_g37.domain.board.Column
import com.example.pdm2324i_gomoku_g37.domain.board.Row
import com.example.pdm2324i_gomoku_g37.domain.dtos.BoardDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.CellDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.ColumnDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.GameDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.PlayerDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.RowDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.RulesDto
import com.example.pdm2324i_gomoku_g37.domain.dtos.UserDto
import java.time.Instant

data class Game(
    val gameId: String,
    val users: Pair<Player, Player>,
    val board: Board,
    val currentPlayer: Player,
    val score: Int,
    val now: Instant,
    val rules: Rules
)

data class GameInfo(
    val id: String,
    val userB: User,
    val userW: User,
    val turn: String,
    val rules: Rules,
    val boardCells: Map<Cell, Turn>,
    val boardState: String
)

fun GameInfo.toGame(): Game {
    val players = Pair(Player(userB, Turn.BLACK_PIECE), Player(userW, Turn.WHITE_PIECE))
    val currentPlayer: Player = if (userB.username == turn) Player(userB, Turn.BLACK_PIECE)
                        else Player(userW, Turn.WHITE_PIECE)
    return Game(
        id,
        players,
        BoardRun(boardCells, currentPlayer.second, rules.boardDim),
        currentPlayer,
        0,
        Instant.now(),
        rules
    )
}

fun GameDto.toGame(): Game = Game(
    gameId = gameId,
    users = usersDtoToPlayers(user1, user2),
    board = board.toBoard(),
    currentPlayer = currentPlayer.toPlayer(),
    score = score,
    now = now,
    rules = rules.toRules()
)

fun Game.toGameDto(): GameDto = GameDto(
    gameId = gameId,
    user1 = users.first.toPlayerDto(),
    user2 = users.second.toPlayerDto(),
    board = board.toBoardDto(),
    currentPlayer = currentPlayer.toPlayerDto(),
    score = score,
    now = now,
    rules = rules.toRulesDto()
)

private fun UserDto.toUser(): User = User(id, username)

private fun PlayerDto.toPlayer(): Player = Player(user.toUser(), turn)

private fun usersDtoToPlayers(user1: PlayerDto, user2: PlayerDto): Pair<Player, Player> =
    Pair(user1.toPlayer(), user2.toPlayer())

private fun BoardDto.toBoard(): Board = BoardDraw(
    positions.entries.associateTo(mutableMapOf()) { entry ->
        val (key, value) = entry
        Pair(Cell.invoke(Row.invoke(key.row.number), Column.invoke(key.col.symbol)), value)
    },
    boardSize
)

private fun RulesDto.toRules(): Rules = Rules(boardDim, opening, variant)

private fun Player.toPlayerDto(): PlayerDto = PlayerDto(UserDto(first.id, first.username), second)

private fun Board.toBoardDto(): BoardDto = BoardDto(
    positions.entries.associateTo(mutableMapOf()) { entry ->
        val (key, value) = entry
        Pair(CellDto(RowDto(key.row.number), ColumnDto(key.col.symbol)), value)
    },
    boardSize
)

private fun Rules.toRulesDto(): RulesDto = RulesDto(boardDim, opening, variant)