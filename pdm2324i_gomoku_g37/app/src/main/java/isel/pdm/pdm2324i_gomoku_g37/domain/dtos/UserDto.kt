package isel.pdm.pdm2324i_gomoku_g37.domain.dtos

import isel.pdm.pdm2324i_gomoku_g37.domain.User
import isel.pdm.pdm2324i_gomoku_g37.service.utils.SirenModel


typealias UserDto = SirenModel<UserDtoProperties>

data class UserDtoProperties(val userId: String, val username: String, val encodedPassword: String)

val UserDtoType = SirenModel.getType<UserDtoProperties>()

fun UserDtoProperties.toUser() = User(userId, username, encodedPassword)