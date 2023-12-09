package com.example.pdm2324i_gomoku_g37.screens.new_lobby

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.domain.Game
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.Loading
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.domain.board.BOARD_DIM
import com.example.pdm2324i_gomoku_g37.domain.board.boardSizeString
import com.example.pdm2324i_gomoku_g37.domain.boardSizeList
import com.example.pdm2324i_gomoku_g37.domain.exceptionOrNull
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.openingsList
import com.example.pdm2324i_gomoku_g37.domain.toOpening
import com.example.pdm2324i_gomoku_g37.domain.toOpeningString
import com.example.pdm2324i_gomoku_g37.domain.toVariant
import com.example.pdm2324i_gomoku_g37.domain.toVariantString
import com.example.pdm2324i_gomoku_g37.domain.variantsList
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.LargeCustomTitleView
import com.example.pdm2324i_gomoku_g37.screens.components.LoadingAlert
import com.example.pdm2324i_gomoku_g37.screens.components.ProcessError
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme

data class NewGameScreenState(
    val game: LoadState<Game?> = idle(),
    val selectedBoardSize: Int = BOARD_DIM,
    val isBoardSizeInputExpanded: Boolean = false,
    val selectedGameOpening: Opening = Opening.FREESTYLE,
    val isGameOpeningInputExpanded: Boolean = false,
    val selectedGameVariant: Variant = Variant.FREESTYLE,
    val isGameVariantInputExpanded: Boolean = false
)

data class NewGameScreenFunctions(
    val changeSelectedBoardSize: (Int) -> Unit = { },
    val changeIsBoardSizeInputExpanded: () -> Unit = { },
    val changeSelectedGameOpening: (Opening) -> Unit = { },
    val changeIsGameOpeningInputExpanded: () -> Unit = { },
    val changeSelectedGameVariant: (Variant) -> Unit = { },
    val changeIsGameVariantInputExpanded: () -> Unit = { },
    val createNewGameRequested: () -> Unit = { },
    val onDismissError: () -> Unit = { }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewLobbyScreen(
    state: NewGameScreenState = NewGameScreenState(),
    functions: NewGameScreenFunctions = NewGameScreenFunctions()
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { GroupFooterView(Color.White) },
    ) { padding ->
        val customContainerModifier = Modifier
            .padding(padding)
            .fillMaxSize()

        CustomContainerView(modifier = customContainerModifier) {
            LargeCustomTitleView(text = "New Game")
            Text(
                text = "Choose your game settings",
                modifier = Modifier.padding(vertical = 5.dp)
            )
            BoardSizeSelect(
                selectedBoardSize = state.selectedBoardSize,
                changeSelectedBoardSize = functions.changeSelectedBoardSize,
                isExpanded = state.isBoardSizeInputExpanded,
                changeIsExpanded = functions.changeIsBoardSizeInputExpanded
            )
            GameOpeningSelect(
                selectedGameOpening = state.selectedGameOpening,
                changeSelectedGameOpening = functions.changeSelectedGameOpening,
                isExpanded = state.isGameOpeningInputExpanded,
                changeIsExpanded = functions.changeIsGameOpeningInputExpanded
            )
            GameVariantSelect(
                selectedGameVariant = state.selectedGameVariant,
                changeSelectedGameVariant = functions.changeSelectedGameVariant,
                isExpanded = state.isGameVariantInputExpanded,
                changeIsExpanded = functions.changeIsGameVariantInputExpanded
            )
            Button(
                onClick = functions.createNewGameRequested,
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Text(text = "Continue")
            }

            if (state.game is Loading)
                LoadingAlert(R.string.loading_new_game_title, R.string.loading_new_game_message)

            state.game.exceptionOrNull()?.let { cause ->
                ProcessError(functions.onDismissError, cause)
            }
        }
    }
}

@Composable
private fun BoardSizeSelect(
    selectedBoardSize: Int = BOARD_DIM,
    changeSelectedBoardSize: (Int) -> Unit = { },
    isExpanded: Boolean = false,
    changeIsExpanded: () -> Unit = { },
) {
    GameSelect(
        value = selectedBoardSize.boardSizeString(),
        label = { Text("Board size") },
        isExpanded = isExpanded,
        dataList = boardSizeList.map { it.toString() },
        onExpandChange = changeIsExpanded,
        onClick = { label ->
            changeSelectedBoardSize(label.toInt())
        }
    )
}

@Composable
private fun GameOpeningSelect(
    selectedGameOpening: Opening = Opening.FREESTYLE,
    changeSelectedGameOpening: (Opening) -> Unit = { },
    isExpanded: Boolean = false,
    changeIsExpanded: () -> Unit = { },
) {
    GameSelect(
        value = selectedGameOpening.toOpeningString(),
        label = { Text("Opening") },
        isExpanded = isExpanded,
        dataList = openingsList,
        onExpandChange = changeIsExpanded,
        onClick = { label ->
            changeSelectedGameOpening(label.toOpening())
        }
    )
}

@Composable
private fun GameVariantSelect(
    selectedGameVariant: Variant = Variant.FREESTYLE,
    changeSelectedGameVariant: (Variant) -> Unit = { },
    isExpanded: Boolean = false,
    changeIsExpanded: () -> Unit = { },
) {
    GameSelect(
        value = selectedGameVariant.toVariantString(),
        label = { Text("Variant") },
        isExpanded = isExpanded,
        dataList = variantsList,
        onExpandChange = changeIsExpanded,
        onClick = { label ->
            changeSelectedGameVariant(label.toVariant())
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GameSelect(
    value: String,
    label: @Composable () -> Unit,
    isExpanded: Boolean,
    dataList: List<String>,
    onExpandChange: () -> Unit,
    onClick: (String) -> Unit
) {
    var textFieldSize by remember { mutableStateOf(Size.Zero)}
    val textFieldModifier = Modifier
        .onGloballyPositioned { coordinates -> textFieldSize = coordinates.size.toSize() }
        .padding(vertical = 15.dp)

    val dropDownMenuModifier = Modifier
        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })

    Box(contentAlignment = Alignment.Center) {
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            modifier = textFieldModifier,
            label = label,
            trailingIcon = { TrailingIcon(isExpanded, onExpandChange) }
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = onExpandChange,
            modifier = dropDownMenuModifier
        ) {
            dataList.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label) },
                    onClick = {
                        onExpandChange()
                        onClick(label)
                    }
                )
            }
        }
    }
}

@Composable
private fun TrailingIcon(isExpanded: Boolean, changeIsExpanded: () -> Unit) {
    val icon = if (isExpanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown
    IconButton(onClick = changeIsExpanded) {
        Icon(icon, contentDescription = null)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewGameScreenPreview() {
    GomokuTheme {
        NewLobbyScreen()
    }
}