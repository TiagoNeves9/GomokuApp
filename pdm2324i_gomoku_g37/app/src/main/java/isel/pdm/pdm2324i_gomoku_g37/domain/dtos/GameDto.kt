package isel.pdm.pdm2324i_gomoku_g37.domain.dtos

import isel.pdm.pdm2324i_gomoku_g37.domain.Player
import isel.pdm.pdm2324i_gomoku_g37.domain.Rules
import isel.pdm.pdm2324i_gomoku_g37.domain.Turn
import isel.pdm.pdm2324i_gomoku_g37.domain.User
import isel.pdm.pdm2324i_gomoku_g37.domain.board.BOARD_BLACK_WON
import isel.pdm.pdm2324i_gomoku_g37.domain.board.BOARD_DRAW
import isel.pdm.pdm2324i_gomoku_g37.domain.board.BOARD_WHITE_WON
import isel.pdm.pdm2324i_gomoku_g37.domain.board.BoardDraw
import isel.pdm.pdm2324i_gomoku_g37.domain.board.BoardRun
import isel.pdm.pdm2324i_gomoku_g37.domain.board.BoardWin
import isel.pdm.pdm2324i_gomoku_g37.domain.board.Cell
import isel.pdm.pdm2324i_gomoku_g37.domain.board.stringToCell
import isel.pdm.pdm2324i_gomoku_g37.service.utils.SirenModel


typealias GameDto = SirenModel<GameDtoProperties>

data class GameDtoProperties(
    val id: String,
    val userB: User,
    val userW: User,
    val turn: String,
    val rules: Rules,
    val boardCells: Map<String, Turn>,
    val boardState: String
)

val GameDtoType = SirenModel.getType<GameDtoProperties>()

fun GameDtoProperties.toGame(): isel.pdm.pdm2324i_gomoku_g37.domain.Game =
    isel.pdm.pdm2324i_gomoku_g37.domain.Game(
        id,
        Pair(userB, userW),
        computeBoard(
            boardCells.stringToCell(rules.boardDim),
            turn,
            rules.boardDim,
            userB,
            userW,
            boardState
        ),
        computeCurrentPlayer(userB, userW, turn),
        rules
    )

fun computeBoard(
    boardCells: Map<Cell, Turn>,
    turn: String,
    boardDim: Int,
    userB: User,
    userW: User, boardState: String
) = when (boardState) {
    BOARD_BLACK_WON -> BoardWin(boardCells, Player(userB, Turn.BLACK_PIECE), boardDim)
    BOARD_WHITE_WON -> BoardWin(boardCells, Player(userW, Turn.BLACK_PIECE), boardDim)
    BOARD_DRAW -> BoardDraw(boardCells, boardDim)
    else -> BoardRun(boardCells, turn.getTurn(userB.username), boardDim)
}

fun computeCurrentPlayer(userB: User, userW: User, turn: String): Player =
    if (userB.username == turn) Player(userB, Turn.BLACK_PIECE) else Player(userW, Turn.WHITE_PIECE)

fun String.getTurn(userB: String): Turn =
    if (userB == this) Turn.BLACK_PIECE else Turn.WHITE_PIECE