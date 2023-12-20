package com.example.pdm2324i_gomoku_g37.service

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

private val jsonMediaType = "application/json".toMediaType()

fun makeSignUpLoginBody(name: String, password: String) =
    "{\"name\": \"$name\", \"password\": \"$password\"}"
        .toRequestBody(contentType = jsonMediaType)

fun makeCreateLobbyBody(boardDim: Int, opening: String, variant: String) =
    "{\"boardDim\": $boardDim, \"opening\": \"$opening\", \"variant\": \"$variant\"}"
        .toRequestBody(contentType = jsonMediaType)