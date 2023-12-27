package isel.pdm.pdm2324i_gomoku_g37.service

import isel.pdm.pdm2324i_gomoku_g37.domain.Rules
import isel.pdm.pdm2324i_gomoku_g37.domain.WaitingLobby
import isel.pdm.pdm2324i_gomoku_g37.domain.board.Cell
import isel.pdm.pdm2324i_gomoku_g37.domain.toOpeningString
import isel.pdm.pdm2324i_gomoku_g37.domain.toVariantString
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


private val jsonMediaType = "application/json".toMediaType()

fun makeSignUpLoginBody(name: String, password: String) =
    "{\"name\": \"$name\", \"password\": \"$password\"}"
        .toRequestBody(contentType = jsonMediaType)

fun makeCreateLobbyBody(rules: Rules) =
    "{\"boardDim\": ${rules.boardDim}, \"opening\": \"${rules.opening.toOpeningString()}\", \"variant\": \"${rules.variant.toVariantString()}\"}"
        .toRequestBody(contentType = jsonMediaType)

fun makeJoinLobby(lobby: WaitingLobby) =
    "{\"lobbyId\": \"${lobby.lobbyId}\", \"hostUserId\": \"${lobby.hostUserId}\", \"boardDim\": ${lobby.boardDim}, \"opening\": \"${lobby.opening}\", \"variant\": \"${lobby.variant}\"}"
        .toRequestBody(contentType = jsonMediaType)

fun makePlayBody(cell: Cell, boardSize: Int) =
    "{\"row\": ${cell.row.number}, \"col\": \"${cell.col.symbol.uppercase()}\", \"boardSize\": $boardSize}"
        .toRequestBody(contentType = jsonMediaType)