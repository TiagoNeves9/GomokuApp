package isel.pdm.pdm2324i_gomoku_g37.screens.game

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import isel.pdm.pdm2324i_gomoku_g37.helpers.GameScreenTestTags.GameScreenBoardTestTag
import isel.pdm.pdm2324i_gomoku_g37.helpers.GameScreenTestTags.GameScreenClickableCellTestTag
import isel.pdm.pdm2324i_gomoku_g37.screens.components.BOARD_PLUS_SYMBOL_FULL_OFFSET
import isel.pdm.pdm2324i_gomoku_g37.screens.components.BOARD_PLUS_SYMBOL_HALF_OFFSET
import isel.pdm.pdm2324i_gomoku_g37.screens.components.BOARD_PLUS_SYMBOL_STROKE_WIDTH
import isel.pdm.pdm2324i_gomoku_g37.screens.components.BOARD_PLUS_SYMBOL_ZERO_OFFSET
import isel.pdm.pdm2324i_gomoku_g37.screens.components.LoadingAlert
import isel.pdm.pdm2324i_gomoku_g37.screens.components.ProcessError
import isel.pdm.pdm2324i_gomoku_g37.service.GomokuGames
import isel.pdm.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import isel.pdm.pdm2324i_gomoku_g37.R
import isel.pdm.pdm2324i_gomoku_g37.domain.Game
import isel.pdm.pdm2324i_gomoku_g37.domain.LoadState
import isel.pdm.pdm2324i_gomoku_g37.domain.Turn
import isel.pdm.pdm2324i_gomoku_g37.domain.board.BOARD_CELL_SIZE
import isel.pdm.pdm2324i_gomoku_g37.domain.board.Board
import isel.pdm.pdm2324i_gomoku_g37.domain.board.BoardDraw
import isel.pdm.pdm2324i_gomoku_g37.domain.board.BoardRun
import isel.pdm.pdm2324i_gomoku_g37.domain.board.BoardWin
import isel.pdm.pdm2324i_gomoku_g37.domain.board.Cell
import isel.pdm.pdm2324i_gomoku_g37.domain.board.indexToColumn
import isel.pdm.pdm2324i_gomoku_g37.domain.board.indexToRow
import isel.pdm.pdm2324i_gomoku_g37.domain.exceptionOrNull
import isel.pdm.pdm2324i_gomoku_g37.domain.getOrNull


@Composable
fun GameScreen(
    currentGame: LoadState<Game?>,
    selectedCell: Cell?,
    onCellSelected: (Cell) -> Unit = { },
    currentUser: String,
    onPlayRequested: () -> Unit = { },
    onDismissError: () -> Unit = { }
) {
    val containerModifier = Modifier
        .background(Color.DarkGray)
        .fillMaxSize()

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            containerModifier,
            Arrangement.Bottom,
            Alignment.CenterHorizontally
        ) {
            currentGame.getOrNull()?.let { game ->
                Text(text = "${game.users.first.username} VS ${game.users.second.username}")

                DrawBoard(game.board, selectedCell) { cell ->
                    if (game.currentPlayer.first.username == currentUser)
                        onCellSelected(cell)
                }

                if (selectedCell != null)
                    OutlinedButton(onClick = onPlayRequested) {
                        Text(stringResource(id = R.string.make_move_button))
                    }

                Spacer(Modifier.padding(vertical = BOARD_CELL_SIZE.dp))

                StatusBar {
                    when (game.board) {
                        is BoardRun -> {
                            Text(stringResource(id = R.string.turn_text).plus(" ${game.currentPlayer.first.username}"))
                            DrawTurnOrWinnerPiece(game.board)
                        }

                        is BoardWin -> {
                            val message =
                                stringResource(id = R.string.game_winner_message).plus(" ${game.currentPlayer.first.username}")
                            Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
                            DrawTurnOrWinnerPiece(game.board)
                        }

                        is BoardDraw -> {
                            val message = stringResource(id = R.string.game_draw)
                            Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            if (currentGame is isel.pdm.pdm2324i_gomoku_g37.domain.Loading)
                LoadingAlert(R.string.loading_game_title, R.string.loading_game_message)

            currentGame.exceptionOrNull()?.let { cause ->
                ProcessError(onDismissError, cause)
            }
        }
    }
}

@Composable
fun DrawBoard(board: Board, selectedCell: Cell?, onClick: (Cell) -> Unit = {}) {
    SymbolAxisView(board.boardSize)

    Row(
        Modifier.testTag(GameScreenBoardTestTag),
        Arrangement.Center,
        Alignment.Bottom
    ) {
        NumberAxisView(board.boardSize)
        CellsView(board, selectedCell, onClick)
        NumberAxisView(board.boardSize)
    }

    SymbolAxisView(board.boardSize)
}

@Composable
fun SymbolAxisView(boardSize: Int) =
    Row(
        Modifier.padding(top = 10.dp, bottom = 10.dp),
        Arrangement.SpaceEvenly
    ) {
        Box(Modifier.size(BOARD_CELL_SIZE.dp)) {
            AxisText(" ")
        }

        repeat(boardSize) {
            val letter = it.indexToColumn(boardSize).symbol.toString()
            Box(
                Modifier.size(BOARD_CELL_SIZE.dp),
                Alignment.Center
            ) {
                AxisText(letter)
            }
        }

        Box(Modifier.size(BOARD_CELL_SIZE.dp)) {
            AxisText(" ")
        }
    }

@Composable
fun NumberAxisView(boardSize: Int) =
    Column(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        repeat(boardSize) {
            val number = it.indexToRow(boardSize).number.toString()
            Box(
                Modifier.size(BOARD_CELL_SIZE.dp),
                Alignment.Center
            ) {
                AxisText(number)
            }
        }
    }

@Composable
fun AxisText(text: String) = Text(
    text = text,
    color = MaterialTheme.colorScheme.primary,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center
)

@Composable
fun CellsView(board: Board, selectedCell: Cell?, onClick: (Cell) -> Unit = {}) =
    Column {
        repeat(board.boardSize) { line ->
            Row {
                repeat(board.boardSize) { col ->
                    val cell = Cell(line, col, board.boardSize)
                    when (board.positions[cell]) {
                        Turn.BLACK_PIECE -> DrawCells(cell = cell, enabled = false) {
                            DrawBlackPiece()
                        }

                        Turn.WHITE_PIECE -> DrawCells(cell = cell, enabled = false) {
                            DrawWhitePiece()
                        }

                        else -> DrawCells(
                            onClick = onClick,
                            selectedCell = selectedCell,
                            cell = cell,
                            enabled = true
                        )
                    }
                }
            }
        }
    }

@Composable
fun DrawCells(
    onClick: (Cell) -> Unit = {},
    selectedCell: Cell? = null,
    cell: Cell,
    enabled: Boolean,
    content: @Composable () -> Unit = {}
) {
    val padding = (BOARD_CELL_SIZE / 2).dp

    val color = if (selectedCell == cell) Color.Red else Color.White

    val modifier = Modifier
        .testTag(GameScreenClickableCellTestTag)
        .size(BOARD_CELL_SIZE.dp)
        .background(color = color)
        .clickable(enabled = enabled) {
            onClick(cell)
        }

    Box(modifier, Alignment.Center) {
        DrawPlusSymbol(padding)
        content()
    }
}

@Composable
fun DrawPlusSymbol(padding: Dp) {
    val modifier = Modifier
        .fillMaxSize()
        .padding(padding)

    Canvas(modifier) {
        drawLine(
            color = Color.LightGray,
            start = Offset(BOARD_PLUS_SYMBOL_HALF_OFFSET, BOARD_PLUS_SYMBOL_ZERO_OFFSET),
            end = Offset(BOARD_PLUS_SYMBOL_HALF_OFFSET, BOARD_PLUS_SYMBOL_FULL_OFFSET),
            strokeWidth = BOARD_PLUS_SYMBOL_STROKE_WIDTH
        )
        drawLine(
            color = Color.LightGray,
            start = Offset(BOARD_PLUS_SYMBOL_ZERO_OFFSET, BOARD_PLUS_SYMBOL_HALF_OFFSET),
            end = Offset(BOARD_PLUS_SYMBOL_FULL_OFFSET, BOARD_PLUS_SYMBOL_HALF_OFFSET),
            strokeWidth = BOARD_PLUS_SYMBOL_STROKE_WIDTH
        )
    }
}

@Composable
fun DrawBlackPiece() =
    Image(painter = painterResource(id = R.drawable.b), contentDescription = null)

@Composable
fun DrawWhitePiece() =
    Image(painter = painterResource(id = R.drawable.w), contentDescription = null)

@Composable
fun DrawTurnOrWinnerPiece(board: Board) =
    if (board is BoardRun)
        if (board.turn == Turn.BLACK_PIECE) DrawBlackPiece()
        else DrawWhitePiece()
    else if (board is BoardWin)
        if (board.winner.second == Turn.BLACK_PIECE) DrawBlackPiece()
        else DrawWhitePiece()
    else throw IllegalStateException("Game is running or finished with a winner.")

@Composable
fun StatusBar(content: @Composable () -> Unit = {}) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
    Text(
        stringResource(id = R.string.activity_main_footer),
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GameScreenPreview() =
    GomokuTheme {
        val game = GomokuGames.games.first()
        GameScreen(isel.pdm.pdm2324i_gomoku_g37.domain.loaded(Result.success(game)), null, {}, "jp")
    }