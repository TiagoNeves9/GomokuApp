package isel.pdm.pdm2324i_gomoku_g37.screens.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import isel.pdm.pdm2324i_gomoku_g37.R
import isel.pdm.pdm2324i_gomoku_g37.domain.LoadState
import isel.pdm.pdm2324i_gomoku_g37.domain.Loading
import isel.pdm.pdm2324i_gomoku_g37.domain.User
import isel.pdm.pdm2324i_gomoku_g37.domain.UserStatistics
import isel.pdm.pdm2324i_gomoku_g37.domain.exceptionOrNull
import isel.pdm.pdm2324i_gomoku_g37.domain.getOrNull
import isel.pdm.pdm2324i_gomoku_g37.domain.idle
import isel.pdm.pdm2324i_gomoku_g37.screens.components.CustomBar
import isel.pdm.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import isel.pdm.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import isel.pdm.pdm2324i_gomoku_g37.screens.components.LoadingAlert
import isel.pdm.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import isel.pdm.pdm2324i_gomoku_g37.screens.components.ProcessError


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userProfile: LoadState<User?> = idle(),
    userStatistic: LoadState<UserStatistics?> = idle(),
    onDismissProfileError: () -> Unit = {},
    onDismissStatisticError: () -> Unit = { },
    navigation: NavigationHandlers = NavigationHandlers()
) =
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CustomBar(
                text = stringResource(R.string.activity_profile_top_bar_title),
                navigation
            )
        },
        bottomBar = { GroupFooterView() }
    ) { padding ->
        CustomContainerView(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            val profileInfo = userProfile.getOrNull()
            if (profileInfo != null) {
                Text(stringResource(R.string.userid_text).plus(" ${profileInfo.userId}"))
                Text(stringResource(R.string.username_text).plus(" ${profileInfo.username}"))
            } else Text(text = stringResource(R.string.no_profile_info_found))

            if (userProfile is Loading)
                LoadingAlert(
                    R.string.loading_user_profile_title,
                    R.string.loading_user_profile_message
                )

            userProfile.exceptionOrNull()?.let { cause ->
                ProcessError(onDismissProfileError, cause)
            }

            val stat = userStatistic.getOrNull()
            if (stat != null) {
                Text(text = stringResource(R.string.profile_screen_total_games).plus(" ${stat.ngames}"))
                Text(text = stringResource(R.string.profile_total_score).plus(" ${stat.score}"))
            } else Text(text = stringResource(R.string.profile_no_stats_found))

            if (userStatistic is Loading)
                LoadingAlert(
                    R.string.loading_user_profile_title,
                    R.string.loading_user_profile_message
                )

            userStatistic.exceptionOrNull()?.let { cause ->
                ProcessError(onDismissStatisticError, cause)
            }
        }
    }

@Preview
@Composable
fun ProfileScreenPreview() = ProfileScreen()