package com.example.pdm2324i_gomoku_g37.screens.new_game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.pdm2324i_gomoku_g37.domain.Opening
import com.example.pdm2324i_gomoku_g37.domain.Variant
import com.example.pdm2324i_gomoku_g37.domain.board.BIG_BOARD_DIM
import com.example.pdm2324i_gomoku_g37.domain.board.BOARD_DIM
import com.example.pdm2324i_gomoku_g37.domain.board.boardSizeString
import com.example.pdm2324i_gomoku_g37.domain.boardSizeList
import com.example.pdm2324i_gomoku_g37.domain.openingsList
import com.example.pdm2324i_gomoku_g37.domain.toOpening
import com.example.pdm2324i_gomoku_g37.domain.toOpeningString
import com.example.pdm2324i_gomoku_g37.domain.toVariant
import com.example.pdm2324i_gomoku_g37.domain.toVariantString
import com.example.pdm2324i_gomoku_g37.domain.variantsList
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.LargeCustomTitleView
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGameScreen() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { GroupFooterView(Color.White) },
    ) { padding ->
        val customContainerModifier = Modifier
            .padding(padding)
            .fillMaxWidth()

        CustomContainerView(modifier = customContainerModifier) {
            LargeCustomTitleView(text = "New Game")
            Text("Choose your game settings")
            BoardSizeSelect()
            GameOpeningSelect()
            GameVariantSelect()
        }
    }
}

@Composable
private fun BoardSizeSelect() {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedBoardSize by remember { mutableIntStateOf(BOARD_DIM) }

    GameSelect(
        value = selectedBoardSize.boardSizeString(),
        label = { Text("Board size") },
        isExpanded = isExpanded,
        dataList = boardSizeList.map { it.toString() },
        onExpandChange = { isExpanded = !isExpanded },
        onDismissRequest = { isExpanded = false },
        onClick = { label ->
            selectedBoardSize = label.toInt()
            isExpanded = false
        }
    )
}

@Composable
private fun GameOpeningSelect() {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedGameOpening by remember { mutableStateOf(Opening.FREESTYLE) }

    GameSelect(
        value = selectedGameOpening.toOpeningString(),
        label = { Text("Opening") },
        isExpanded = isExpanded,
        dataList = openingsList,
        onExpandChange = { isExpanded = !isExpanded },
        onDismissRequest = { isExpanded = false },
        onClick = { label ->
            selectedGameOpening = label.toOpening()
            isExpanded = false
        }
    )
}

@Composable
private fun GameVariantSelect() {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedGameVariant by remember { mutableStateOf(Variant.FREESTYLE) }

    GameSelect(
        value = selectedGameVariant.toVariantString(),
        label = { Text("Variant") },
        isExpanded = isExpanded,
        dataList = variantsList,
        onExpandChange = { isExpanded = !isExpanded },
        onDismissRequest = { isExpanded = false },
        onClick = { label ->
            selectedGameVariant = label.toVariant()
            isExpanded = false
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
    onDismissRequest: () -> Unit,
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
            onDismissRequest = onDismissRequest,
            modifier = dropDownMenuModifier
        ) {
            dataList.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label) },
                    onClick = { onClick(label) }
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
        NewGameScreen()
    }
}