package isel.pdm.pdm2324i_gomoku_g37.domain

interface UserInfoRepository {

    suspend fun getUserInfo(): UserInfo?

    suspend fun updateUserInfo(userInfo: UserInfo)

    suspend fun clearUserInfo()
}