package com.example.pdm2324i_gomoku_g37.service

import com.example.pdm2324i_gomoku_g37.domain.Rules
import com.example.pdm2324i_gomoku_g37.domain.WaitingLobby
import com.example.pdm2324i_gomoku_g37.domain.toOpeningString
import com.example.pdm2324i_gomoku_g37.domain.toVariantString
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
    "{\"lobbyId\": ${lobby.lobbyId}, \"hostUserId\": \"${lobby.hostUserId}, \"boardDim\": ${lobby.boardDim}, \"opening\": \"${lobby.opening}\", \"variant\": \"${lobby.variant}\"}"
         .toRequestBody(contentType = jsonMediaType)