package com.example.pdm2324i_gomoku_g37.domain

data class UserInfo(val id: Int, val username: String, val token: String) {
    init {
        require(validateUserInfoParts(id, username, token))
    }
}

fun validateUserInfoParts(id: Int, username: String, token: String): Boolean =
    id > 0 && username.isNotBlank() && token.isNotBlank()

fun toUserInfoOrNull(id: Int, username: String, token: String): UserInfo? =
    if (validateUserInfoParts(id, username, token)) UserInfo(id, username, token)
    else null