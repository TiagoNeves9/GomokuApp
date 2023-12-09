package com.example.pdm2324i_gomoku_g37.screens.common

import android.content.Intent
import android.os.Parcelable
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import kotlinx.parcelize.Parcelize

const val USER_INFO_EXTRA = "UserInfo"

/**
 * Represents the data to be passed as an extra in the intents used to navigate to activities
 * that require the user information. We use this class because the [UserInfo] class is not
 * parcelable and we do not want to make it parcelable because it's a domain class.
 */
@Parcelize
data class UserInfoExtra(val id: String, val username: String, val token: String) : Parcelable {
    constructor(userInfo: UserInfo) : this(userInfo.id, userInfo.username, userInfo.token)
}

/**
 * Converts this [UserInfoExtra] to a [UserInfo].
 */
fun UserInfoExtra.toUserInfo() = UserInfo(id, username, token)

@Suppress("DEPRECATION")
fun getUserInfoExtra(intent: Intent): UserInfoExtra? =
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
        intent.getParcelableExtra(USER_INFO_EXTRA, UserInfoExtra::class.java)
    else
        intent.getParcelableExtra(USER_INFO_EXTRA)