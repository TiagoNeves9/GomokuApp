package com.example.pdm2324i_gomoku_g37.domain

data class UserInfo(val id: Int, val username: String, val token: String) {
    init {
        require(validateUserInfoParts(username))
    }
}

fun validateUserInfoParts(username: String): Boolean = username.isNotBlank()

fun toUserInfoOrNull(id: Int, username: String, token: String): UserInfo? =
    if (validateUserInfoParts(username))
        UserInfo(id, username, token)
    else
        null