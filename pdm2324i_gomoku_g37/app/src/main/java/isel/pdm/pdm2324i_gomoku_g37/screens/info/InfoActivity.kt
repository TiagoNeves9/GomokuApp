package isel.pdm.pdm2324i_gomoku_g37.screens.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import isel.pdm.pdm2324i_gomoku_g37.domain.UserInfo
import isel.pdm.pdm2324i_gomoku_g37.domain.idle
import isel.pdm.pdm2324i_gomoku_g37.screens.common.USER_INFO_EXTRA
import isel.pdm.pdm2324i_gomoku_g37.screens.common.UserInfoExtra
import isel.pdm.pdm2324i_gomoku_g37.screens.common.getUserInfoExtra
import isel.pdm.pdm2324i_gomoku_g37.screens.common.toUserInfo
import isel.pdm.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import isel.pdm.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import kotlinx.coroutines.launch


class InfoActivity : ComponentActivity() {
    companion object {
        fun navigateTo(origin: Context, userInfo: UserInfo) {
            origin.startActivity(createIntent(origin, userInfo))
        }

        private fun createIntent(ctx: Context, userInfo: UserInfo): Intent {
            val intent = Intent(ctx, InfoActivity::class.java)
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

    private val dependencies by lazy {
        application as isel.pdm.pdm2324i_gomoku_g37.GomokuDependenciesContainer
    }

    private val viewModel by viewModels<InfoScreenViewModel> {
        InfoScreenViewModel.factory(dependencies.gomokuService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetchInfo()
            }
        }

        setContent {
            val currentApiInfo by viewModel.apiInfoFlow.collectAsState(initial = idle())
            GomokuTheme {
                InfoScreen(
                    info = currentApiInfo,
                    navigation = NavigationHandlers(
                        onBackRequested = { finish() }
                    ),
                    onDismiss = viewModel::resetToIdle
                )
            }
        }
    }
}