package com.example.pdm2324i_gomoku_g37.domain

import com.example.pdm2324i_gomoku_g37.domain.board.Cell
import com.example.pdm2324i_gomoku_g37.domain.board.Piece


const val BOARD_DIM = 15

sealed class Board(val positions: Map<Cell, Piece>)

class BoardRun(positions: Map<Cell, Piece>, val turn: Player) : Board(positions)

class BoardEnd(positions: Map<Cell, Piece>) : Board(positions)

fun createBoard(firstTurn: Player) = BoardRun(mapOf(), firstTurn)

//TODO fun Board.getWinner(): Player? {}

