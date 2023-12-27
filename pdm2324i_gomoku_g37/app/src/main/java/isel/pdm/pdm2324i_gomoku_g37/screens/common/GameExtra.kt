package isel.pdm.pdm2324i_gomoku_g37.screens.common

import android.content.Intent
import android.os.Parcelable
import isel.pdm.pdm2324i_gomoku_g37.domain.Opening
import isel.pdm.pdm2324i_gomoku_g37.domain.Player
import isel.pdm.pdm2324i_gomoku_g37.domain.Rules
import isel.pdm.pdm2324i_gomoku_g37.domain.Turn
import isel.pdm.pdm2324i_gomoku_g37.domain.User
import isel.pdm.pdm2324i_gomoku_g37.domain.Variant
import isel.pdm.pdm2324i_gomoku_g37.domain.board.Board
import isel.pdm.pdm2324i_gomoku_g37.domain.board.BoardRun
import isel.pdm.pdm2324i_gomoku_g37.domain.board.Cell
import isel.pdm.pdm2324i_gomoku_g37.domain.board.Column
import isel.pdm.pdm2324i_gomoku_g37.domain.board.Row
import kotlinx.parcelize.Parcelize


const val GAME_EXTRA = "Game"

@Parcelize
data class GameExtra(
    val gameId: String,
    val userW: UserExtra,
    val userB: UserExtra,
    val board: BoardExtra,
    val currentPlayer: PlayerExtra,
    val rules: RulesExtra
) : Parcelable {
    constructor(game: isel.pdm.pdm2324i_gomoku_g37.domain.Game) : this(
        game.gameId,
        UserExtra(game.users.first),
        UserExtra(game.users.second),
        BoardExtra(game.board),
        PlayerExtra(game.currentPlayer),
        RulesExtra(game.rules)
    )

    fun toGame(): isel.pdm.pdm2324i_gomoku_g37.domain.Game =
        isel.pdm.pdm2324i_gomoku_g37.domain.Game(
            gameId,
            Pair(userW.toUser(), userB.toUser()),
            board.toBoard(currentPlayer.turn),
            currentPlayer.toPlayer(),
            rules.toRules()
        )
}

@Parcelize
data class PlayerExtra(val user: UserExtra, val turn: Turn) : Parcelable {
    constructor(player: Player) : this(UserExtra(player.first), player.second)

    fun toPlayer(): Player = Player(user.toUser(), turn)
}

@Parcelize
data class UserExtra(val id: String, val username: String, val encodedPassword: String) :
    Parcelable {
    constructor(user: User) : this(user.userId, user.username, user.encodedPassword)

    fun toUser(): User = User(id, username, encodedPassword)
}

@Parcelize
data class BoardExtra(val positions: Map<CellExtra, Turn>, val boardSize: Int) : Parcelable {
    constructor(board: Board) : this(board.positions.toCellDto(), board.boardSize)

    fun toBoard(turn: Turn): Board = BoardRun(
        positions.mapKeys { (cellExtra, _) -> cellExtra.toCell(boardSize) },
        turn,
        boardSize
    )
}

@Parcelize
data class CellExtra(val row: RowExtra, val col: ColumnExtra) : Parcelable {
    constructor(cell: Cell) : this(RowExtra(cell.row), ColumnExtra(cell.col))

    fun toCell(boardSize: Int): Cell =
        Cell.invoke(row.toRow(boardSize), col.toColumn(boardSize), boardSize)
}

@Parcelize
data class RowExtra(val number: Int) : Parcelable {
    constructor(row: Row) : this(row.number)

    fun toRow(boardSize: Int): Row = Row.invoke(number, boardSize)
}

@Parcelize
data class ColumnExtra(val symbol: Char) : Parcelable {
    constructor(column: Column) : this(column.symbol)

    fun toColumn(boardSize: Int): Column = Column.invoke(symbol, boardSize)
}

@Parcelize
data class RulesExtra(val boardDim: Int, val opening: Opening, val variant: Variant) : Parcelable {
    constructor(rules: Rules) : this(rules.boardDim, rules.opening, rules.variant)

    fun toRules(): Rules = Rules(boardDim, opening, variant)
}

private fun Map<Cell, Turn>.toCellDto(): Map<CellExtra, Turn> = this.mapKeys { (cell, _) ->
    CellExtra(cell)
}

@Suppress("DEPRECATION")
fun getGameExtra(intent: Intent): GameExtra? =
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
        intent.getParcelableExtra(GAME_EXTRA, GameExtra::class.java)
    else
        intent.getParcelableExtra(GAME_EXTRA)