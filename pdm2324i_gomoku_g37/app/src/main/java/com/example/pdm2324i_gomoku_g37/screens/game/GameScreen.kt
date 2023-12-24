package com.example.pdm2324i_gomoku_g37.screens.game

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.Loaded
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.Turn
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.domain.board.BOARD_CELL_SIZE
import com.example.pdm2324i_gomoku_g37.domain.board.BOARD_DIM
import com.example.pdm2324i_gomoku_g37.domain.board.Board
import com.example.pdm2324i_gomoku_g37.domain.board.BoardDraw
import com.example.pdm2324i_gomoku_g37.domain.board.BoardRun
import com.example.pdm2324i_gomoku_g37.domain.board.BoardWin
import com.example.pdm2324i_gomoku_g37.domain.board.Cell
import com.example.pdm2324i_gomoku_g37.domain.board.createBoard
import com.example.pdm2324i_gomoku_g37.domain.board.indexToColumn
import com.example.pdm2324i_gomoku_g37.domain.board.indexToRow
import com.example.pdm2324i_gomoku_g37.domain.getOrNull
import com.example.pdm2324i_gomoku_g37.domain.loaded
import com.example.pdm2324i_gomoku_g37.service.GomokuGames
import com.example.pdm2324i_gomoku_g37.service.GomokuUsers
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import java.time.Instant


@Composable
fun GameScreen(currentGame: LoadState<Game?>) {

    val modifier = Modifier
        .background(Color.DarkGray)
        .fillMaxSize()

    GomokuTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier,
                Arrangement.Bottom,
                Alignment.CenterHorizontally
            ) {
                currentGame.getOrNull()?.let { game ->
                    DrawBoard(game.board) { cell ->
                        //currentGame = currentGame.computeNewGame(cell)
                    }

                    Spacer(Modifier.padding(vertical = BOARD_CELL_SIZE.dp))

                    StatusBar {
                        //TODO mudar o tamanho da imagem ou do texto
                        when (val currentBoard = game.board) {
                            is BoardRun -> {
                                Text("Turn: ", color = Color.Red)
                                DrawTurnOrWinnerPiece(currentBoard)
                            }

                            is BoardWin -> {
                                Text("Game finished! Winner: ", color = Color.Red)
                                DrawTurnOrWinnerPiece(currentBoard)
                            }

                            is BoardDraw ->
                                Text("Game finished! It's a draw!", color = Color.Red)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawBoard(board: Board, onClick: (Cell) -> Unit = {}) {
    SymbolAxisView(board.boardSize)

    Row(
        Modifier.testTag("boardTest"),
        Arrangement.Center,
        Alignment.Bottom
    ) {
        NumberAxisView(board.boardSize)
        CellsView(board, onClick)
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
fun AxisText(text: String) =
    Text(
        text = text,
        color = Color.Blue,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )

@Composable
fun CellsView(board: Board, onClick: (Cell) -> Unit = {}) =
    Column {
        repeat(board.boardSize) { line ->
            Row {
                repeat(board.boardSize) { col ->
                    val cell = Cell(line, col, board.boardSize)
                    when (board.positions[cell]) {
                        Turn.BLACK_PIECE -> DrawCells(enabled = false) {
                            DrawBlackPiece()
                        }

                        Turn.WHITE_PIECE -> DrawCells(enabled = false) {
                            DrawWhitePiece()
                        }

                        else -> DrawCells({ onClick(cell) }, enabled = true)
                    }
                }
            }
        }
    }

@Composable
fun DrawCells(
    onClick: () -> Unit = {},
    enabled: Boolean,
    content: @Composable () -> Unit = {}
) {
    val padding = (BOARD_CELL_SIZE / 2).dp
    val modifier = Modifier
        .testTag("clickableCell")
        .size(BOARD_CELL_SIZE.dp)
        .background(color = Color.White)
        .clickable(enabled = enabled) { onClick() }
    Box(
        modifier,
        Alignment.Center
    ) {
        DrawPlusSymbol(padding)

        content()
    }
}

@Composable
fun DrawPlusSymbol(padding: Dp) =
    Canvas(
        Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        drawLine(
            color = Color.LightGray,
            start = Offset(0.5F, 0F),
            end = Offset(0.5F, 1F),
            strokeWidth = 60F   //TODO 60F é hard-coded
        )
        drawLine(
            color = Color.LightGray,
            start = Offset(0F, 0.5F),
            end = Offset(1F, 0.5F),
            strokeWidth = 60F   //TODO 60F é hard-coded
        )
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
        verticalAlignment = Alignment.Bottom
    ) {
        content()
    }
    Text("Group 37 - Gomoku ", color = Color.Blue)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GameScreenPreview() {
    val playerB = Player(
        GomokuUsers.users[0],
        Turn.BLACK_PIECE
    )
    val playerW = Player(
        GomokuUsers.users[1],
        Turn.WHITE_PIECE
    )
    val board = createBoard(playerB.second, BOARD_DIM)
    val rules = Rules(board.boardSize, Opening.FREESTYLE, Variant.FREESTYLE)
    val game = GomokuGames.games.first()
    GameScreen(loaded(Result.success(game)))
}