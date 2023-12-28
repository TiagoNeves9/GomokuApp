package isel.pdm.pdm2324i_gomoku_g37.domain.dtos

import isel.pdm.pdm2324i_gomoku_g37.domain.UserInfo
import isel.pdm.pdm2324i_gomoku_g37.service.utils.SirenModel


typealias UserInfoDto = SirenModel<UserInfoDtoProperties>

data class UserInfoDtoProperties(val username: String, val id: String, val token: String)

val UserInfoDtoType = SirenModel.getType<UserInfoDtoProperties>()

fun UserInfoDtoProperties.toUserInfo() = UserInfo(id, username, token)