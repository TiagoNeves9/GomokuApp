package isel.pdm.pdm2324i_gomoku_g37.screens.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import isel.pdm.pdm2324i_gomoku_g37.domain.UserInfo
import isel.pdm.pdm2324i_gomoku_g37.screens.authors.AuthorsActivity
import isel.pdm.pdm2324i_gomoku_g37.screens.common.USER_INFO_EXTRA
import isel.pdm.pdm2324i_gomoku_g37.screens.common.UserInfoExtra
import isel.pdm.pdm2324i_gomoku_g37.screens.common.getUserInfoExtra
import isel.pdm.pdm2324i_gomoku_g37.screens.common.toUserInfo
import isel.pdm.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import isel.pdm.pdm2324i_gomoku_g37.screens.info.InfoActivity
import isel.pdm.pdm2324i_gomoku_g37.screens.login.LoginActivity
import isel.pdm.pdm2324i_gomoku_g37.screens.play.PlayActivity
import isel.pdm.pdm2324i_gomoku_g37.screens.profile.ProfileActivity
import isel.pdm.pdm2324i_gomoku_g37.screens.rankings.RankingsActivity
import isel.pdm.pdm2324i_gomoku_g37.ui.theme.GomokuTheme


class HomeActivity : ComponentActivity() {
    companion object {
        fun navigateTo(origin: Context, userInfo: UserInfo) {
            origin.startActivity(createIntent(origin, userInfo))
        }

        private fun createIntent(ctx: Context, userInfo: UserInfo): Intent {
            val intent = Intent(ctx, HomeActivity::class.java)
            intent.putExtra(USER_INFO_EXTRA, UserInfoExtra(userInfo))
            return intent
        }
    }

    private val userInfoExtra: UserInfo by lazy {
        checkNotNull(getUserInfoExtra(intent)).toUserInfo()
    }

    private val dependencies by lazy {
        application as isel.pdm.pdm2324i_gomoku_g37.GomokuDependenciesContainer
    }

    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModel.factory(dependencies.userInfoRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomokuTheme {
                HomeScreen(
                    userInfo = userInfoExtra,
                    navigation = NavigationHandlers(
                        onBackRequested = { finish() },
                        onInfoRequested = {
                            InfoActivity.navigateTo(
                                origin = this,
                                userInfo = userInfoExtra
                            )
                        }
                    ),
                    error = viewModel.error,
                    onAuthorsRequested = {
                        AuthorsActivity.navigateTo(
                            origin = this,
                            userInfo = userInfoExtra
                        )
                    },
                    onPlayRequested = {
                        PlayActivity.navigateTo(
                            origin = this,
                            userInfo = userInfoExtra
                        )
                    },
                    onRankingsRequested = {
                        RankingsActivity.navigateTo(
                            origin = this,
                            userInfo = userInfoExtra
                        )
                    },
                    onProfileRequest = {
                        ProfileActivity.navigateTo(
                            origin = this,
                            userInfo = userInfoExtra
                        )
                    },
                    onLogoutRequested = { doLogout() },
                    onDismissError = viewModel::onDismissError
                )
            }
        }
    }

    private fun doLogout() {
        viewModel.logout()
        LoginActivity.navigateTo(origin = this)
        finish()
    }
}