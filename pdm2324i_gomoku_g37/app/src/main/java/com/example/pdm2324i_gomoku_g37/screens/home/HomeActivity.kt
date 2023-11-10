package com.example.pdm2324i_gomoku_g37.screens.home

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.screens.authors.AuthorsActivity
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import kotlinx.parcelize.Parcelize


class HomeActivity : ComponentActivity() {
    companion object {
        fun navigateTo(origin: ComponentActivity, userInfo: UserInfo) {
            with(origin) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra(USER_INFO_EXTRA, UserInfoExtra(userInfo))
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContent {
            getUserInfoExtra()?.let { userInfoExtra ->
                HomeScreen(
                    userInfo = userInfoExtra.toUserInfo(),
                    onAuthorsRequested = {
                        AuthorsActivity.navigateTo(origin = this)
                    }
                )
            }

        }
    }

    /**
     * Helper method to get the userinfo extra from the intent.
     */
    @Suppress("DEPRECATION")
    private fun getUserInfoExtra(): UserInfoExtra? =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
            intent.getParcelableExtra(USER_INFO_EXTRA, UserInfoExtra::class.java)
        else
            intent.getParcelableExtra(USER_INFO_EXTRA)
}

private const val USER_INFO_EXTRA = "HomeActivity.extra.UserInfo"

@Parcelize
private data class UserInfoExtra(val id: Int, val username: String) : Parcelable {
    constructor(userInfo: UserInfo) : this(userInfo.id, userInfo.username)
}

private fun UserInfoExtra.toUserInfo() = UserInfo(id, username)