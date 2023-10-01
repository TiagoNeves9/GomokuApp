package com.example.pdm2324i_gomoku_g37.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pdm2324i_gomoku_g37.domain.BOARD_CELL_SIZE
import com.example.pdm2324i_gomoku_g37.domain.BOARD_DIM
import com.example.pdm2324i_gomoku_g37.domain.Board
import com.example.pdm2324i_gomoku_g37.domain.BoardRun
import com.example.pdm2324i_gomoku_g37.domain.Player
import com.example.pdm2324i_gomoku_g37.domain.board.Cell
import com.example.pdm2324i_gomoku_g37.domain.board.Piece
import com.example.pdm2324i_gomoku_g37.domain.board.indexToColumn
import com.example.pdm2324i_gomoku_g37.domain.board.indexToRow
import com.example.pdm2324i_gomoku_g37.domain.createBoard
import com.example.pdm2324i_gomoku_g37.ui.theme.Pdm2324i_gomoku_g37Theme


@Composable
fun GameScreen() {
    Pdm2324i_gomoku_g37Theme {
        val playerBlack = Player("BlackPlayer", Piece.BLACK_PIECE)
        var board by remember { mutableStateOf(createBoard(playerBlack)) }
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DrawBoard(board = board) { cell ->
                    board = board.addPiece(cell)
                }
                Spacer(modifier = Modifier.padding(vertical = BOARD_CELL_SIZE.dp))
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Group 37 - XPTO")
                }
            }
        }
    }
}

@Composable
fun DrawBoard(board: Board, onClick: (Cell) -> Unit) {
    SymbolAxisView()
    Row(
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
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.size(BOARD_CELL_SIZE.dp)) {
            Text(
                //To align the symbols
                textAlign = TextAlign.Center, text = " ",
                fontWeight = FontWeight.Bold, color = Color.Green
            )
        }
        repeat(BOARD_DIM) {
            val letter = it.indexToColumn().symbol.toString()
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(BOARD_CELL_SIZE.dp)
            ) {
                Text(
                    textAlign = TextAlign.Center, text = letter,
                    fontWeight = FontWeight.Bold, color = Color.Blue
                )
            }
        }
        Box(modifier = Modifier.size(BOARD_CELL_SIZE.dp)) {
            Text(
                //To align the symbols
                textAlign = TextAlign.Center, text = " ",
                fontWeight = FontWeight.Bold, color = Color.Green
            )
        }
    }
}


@Composable
fun NumberAxisView() {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        repeat(BOARD_DIM) {
            val number = it.indexToRow().number.toString()
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(BOARD_CELL_SIZE.dp)
            ) {
                Text(
                    textAlign = TextAlign.Center, text = number,
                    fontWeight = FontWeight.Bold, color = Color.Blue
                )
            }
        }
    }
}


@Composable
fun CellsView(board: Board, onClick: (Cell) -> Unit) {
    Column {
        for (line in 0 until BOARD_DIM) {
            Row {
                repeat(BOARD_DIM) { col ->
                    val cell = Cell(line, col)
                    CellsSquares(board, onClick, cell)
                }
            }
        }
    }
}

@Composable
fun CellsSquares(board: Board, onClick: (Cell) -> Unit, cell: Cell) {
    val padding = (BOARD_CELL_SIZE / 2).dp

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(BOARD_CELL_SIZE.dp)
            .background(color = Color.White)
            .clickable(onClick = { onClick(cell) }) //TODO mudar o clickable
        //TODO redesenhar a board após o onClick (com a nova peça colocada)
    ) {
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

        val piece: Piece? = board.positions[cell]

        when (piece) {
            //TODO alterar este padding horizontal, está hard-coded
            Piece.BLACK_PIECE -> Text("B", Modifier.padding(horizontal = padding / 2))
            Piece.WHITE_PIECE -> Text("W", Modifier.padding(horizontal = padding / 2))
            else -> Text(" ", Modifier.padding(horizontal = padding / 2))
        }
    }
}

@Preview
@Composable
fun GameScreenPreview() {
    GameScreen()
}