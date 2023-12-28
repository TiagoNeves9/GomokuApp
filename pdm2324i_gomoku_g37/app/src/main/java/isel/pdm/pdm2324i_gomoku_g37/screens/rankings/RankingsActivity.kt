package isel.pdm.pdm2324i_gomoku_g37.screens.rankings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import isel.pdm.pdm2324i_gomoku_g37.GomokuDependenciesContainer
import isel.pdm.pdm2324i_gomoku_g37.domain.UserInfo
import isel.pdm.pdm2324i_gomoku_g37.domain.idle
import isel.pdm.pdm2324i_gomoku_g37.screens.common.USER_INFO_EXTRA
import isel.pdm.pdm2324i_gomoku_g37.screens.common.UserInfoExtra
import isel.pdm.pdm2324i_gomoku_g37.screens.common.getUserInfoExtra
import isel.pdm.pdm2324i_gomoku_g37.screens.common.toUserInfo
import isel.pdm.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import isel.pdm.pdm2324i_gomoku_g37.screens.info.InfoActivity
import isel.pdm.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import kotlinx.coroutines.launch


class RankingsActivity : ComponentActivity() {
    private val dependencies by lazy {
        application as GomokuDependenciesContainer
    }
    private val viewModel by viewModels<RankingsScreenViewModel> {
        RankingsScreenViewModel.factory(dependencies.gomokuService)
    }

    companion object {
        fun navigateTo(origin: Context, userInfo: UserInfo) {
            origin.startActivity(createIntent(origin, userInfo))
        }

        private fun createIntent(ctx: Context, userInfo: UserInfo): Intent {
            val intent = Intent(ctx, RankingsActivity::class.java)
            intent.putExtra(USER_INFO_EXTRA, UserInfoExtra(userInfo))
            return intent
        }
    }

    /**
     * Helper method to get the user info extra from the intent.
     */
    private val userInfoExtra: UserInfo by lazy {
        checkNotNull(getUserInfoExtra(intent)).toUserInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.fetchRankings()
        }

        setContent {
            val currentRankings by viewModel.rankings.collectAsState(initial = idle())
            GomokuTheme {
                RankingsScreen(
                    rankings = currentRankings,
                    onDismissError = viewModel::resetToIdle,
                    navigation = NavigationHandlers(
                        onBackRequested = { finish() },
                        onInfoRequested = {
                            InfoActivity.navigateTo(origin = this, userInfo = userInfoExtra)
                        }
                    )
                )
            }
        }
    }
}