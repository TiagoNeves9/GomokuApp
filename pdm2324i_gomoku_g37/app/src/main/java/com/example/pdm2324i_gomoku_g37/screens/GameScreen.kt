package com.example.pdm2324i_gomoku_g37.screens

import android.util.Log
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
import androidx.compose.material3.TextButton
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
import com.example.pdm2324i_gomoku_g37.domain.BOARD_CELL_SIZE
import com.example.pdm2324i_gomoku_g37.domain.BOARD_DIM
import com.example.pdm2324i_gomoku_g37.domain.Board
import com.example.pdm2324i_gomoku_g37.domain.BoardEnd
import com.example.pdm2324i_gomoku_g37.domain.BoardRun
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.board.Cell
import com.example.pdm2324i_gomoku_g37.domain.board.Piece
import com.example.pdm2324i_gomoku_g37.domain.board.indexToColumn
import com.example.pdm2324i_gomoku_g37.domain.board.indexToRow
import com.example.pdm2324i_gomoku_g37.domain.createBoard
import com.example.pdm2324i_gomoku_g37.image
import com.example.pdm2324i_gomoku_g37.ui.theme.Pdm2324i_gomoku_g37Theme

@Composable
fun GameScreen(game: Game) {
    var currentGame by remember { mutableStateOf(game) }

    val modifier: Modifier = Modifier
        .background(Color.DarkGray)
        .fillMaxSize()

    Pdm2324i_gomoku_g37Theme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                DrawBoard(currentGame.board) { cell ->
                    currentGame = currentGame.computeNewGame(cell)
                }

                Spacer(modifier = Modifier.padding(vertical = BOARD_CELL_SIZE.dp))

                StatusBar {
                    //TODO mudar o tamanho da imagem ou do texto
                    if (currentGame.board is BoardEnd)
                        Text("Game finished!", color = Color.Red)
                    else {
                        Text("Turn: ", color = Color.Red)
                        if (currentGame.currentPlayer.color == Piece.BLACK_PIECE) DrawBlackPiece()
                        else DrawWhitePiece()
                    }
                }
            }
        }
    }
}

@Composable
fun DrawBoard(board: Board, onClick: (Cell) -> Unit = {}) {
    SymbolAxisView()
    Row(
        modifier = Modifier.testTag("boardTest"),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        NumberAxisView()
        CellsView(board, onClick)
        NumberAxisView()
    }
    SymbolAxisView()
}

@Composable
fun SymbolAxisView() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
    ) {
        Box(modifier = Modifier.size(BOARD_CELL_SIZE.dp)) {
            AxisText(" ")
        }
        repeat(BOARD_DIM) {
            val letter = it.indexToColumn().symbol.toString()
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(BOARD_CELL_SIZE.dp)
            ) {
                AxisText(letter)
            }
        }
        Box(modifier = Modifier.size(BOARD_CELL_SIZE.dp)) {
            AxisText(" ")
        }
    }
}

@Composable
fun NumberAxisView() {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    ) {
        repeat(BOARD_DIM) {
            val number = it.indexToRow().number.toString()
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(BOARD_CELL_SIZE.dp)
            ) {
                AxisText(number)
            }
        }
    }
}

@Composable
fun AxisText(text: String) {
    Text(
        textAlign = TextAlign.Center,
        text = text,
        fontWeight = FontWeight.Bold,
        color = Color.Blue
    )
}

@Composable
fun CellsView(board: Board, onClick: (Cell) -> Unit = {}) {
    Column {
        repeat (BOARD_DIM) { line ->
            Row {
                repeat(BOARD_DIM) { col ->
                    val cell = Cell(line, col)
                    when (board.positions[cell]) {
                        Piece.BLACK_PIECE -> DrawCells(enabled = false) {
                                DrawBlackPiece()
                        }

                        Piece.WHITE_PIECE -> DrawCells(enabled = false) {
                                DrawWhitePiece()
                        }

                        else -> DrawCells({ onClick(cell) }, enabled = true)
                    }
                }
            }
        }
    }
}

@Composable
fun DrawCells(onClick: () -> Unit = {}, enabled: Boolean, content: @Composable () -> Unit = {}) {
    val padding = (BOARD_CELL_SIZE / 2).dp
    val modifier = Modifier
        .testTag("clickableCell")
        .size(BOARD_CELL_SIZE.dp)
        .background(color = Color.White)
        .clickable(enabled = enabled) {
            onClick()
        }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        DrawPlusSymbol(padding)
        content()
    }
}

@Composable
fun DrawPlusSymbol(padding: Dp) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        drawLine(
            start = Offset(0.5F, 0F),
            end = Offset(0.5F, 1F),
            color = Color.LightGray,
            strokeWidth = 60F   //TODO 60F é hard-coded
        )
        drawLine(
            start = Offset(0F, 0.5F),
            end = Offset(1F, 0.5F),
            color = Color.LightGray,
            strokeWidth = 60F   //TODO 60F é hard-coded
        )
    }
}

@Composable
fun DrawBlackPiece() {
    Image(painter = painterResource(id = R.drawable.b), contentDescription = null)
}

@Composable
fun DrawWhitePiece() {
    Image(painter = painterResource(id = R.drawable.w), contentDescription = null)
}

@Composable
fun StatusBar(content: @Composable () -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        content()
    }

    Text("Group 37 - Gomoku ", color = Color.Blue)
}

@Preview
@Composable
fun GameScreenPreview() {
    val playerBlack = Player("BlackPlayer", Piece.BLACK_PIECE)
    val playerWhite = Player("WhitePlayer", Piece.WHITE_PIECE)
    val board = createBoard(playerBlack.color)
    val game = Game(Pair(playerBlack, playerWhite), board, playerBlack)
    GameScreen(game)
}