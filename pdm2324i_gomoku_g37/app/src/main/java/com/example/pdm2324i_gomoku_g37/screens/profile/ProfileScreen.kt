package com.example.pdm2324i_gomoku_g37.screens.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.Loading
import com.example.pdm2324i_gomoku_g37.domain.User
import com.example.pdm2324i_gomoku_g37.domain.UserStatistics
import com.example.pdm2324i_gomoku_g37.domain.exceptionOrNull
import com.example.pdm2324i_gomoku_g37.domain.getOrNull
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.LoadingAlert
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.components.ProcessError


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userProfile: LoadState<User?> = idle(),
    userStatistic: LoadState<UserStatistics?> = idle(),
    onDismissProfileError: () -> Unit = {},
    onDismissStatisticError: () -> Unit = { },
    navigation: NavigationHandlers = NavigationHandlers()
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CustomBar(
                text = stringResource(id = R.string.activity_profile_top_bar_title),
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
                Text(stringResource(id = R.string.username_text).plus(" ${profileInfo.username}"))
            } else {
                Text(text = stringResource(id = R.string.no_profile_info_found))
            }

            if (userProfile is Loading)
                LoadingAlert(R.string.loading_user_profile_title, R.string.loading_user_profile_message)

            userProfile.exceptionOrNull()?.let { cause ->
                ProcessError(onDismissProfileError, cause)
            }

            val statistic = userStatistic.getOrNull()

            if (statistic != null) {
                Text(text = stringResource(id = R.string.profile_screen_total_games).plus(" ${statistic.ngames}"))
                Text(text = stringResource(id = R.string.profile_total_score).plus(" ${statistic.score}"))
            } else {
                Text(text = stringResource(id = R.string.profile_no_stats_found))
            }

            if (userStatistic is Loading)
                LoadingAlert(R.string.loading_user_profile_title, R.string.loading_user_profile_message)

            userStatistic.exceptionOrNull()?.let { cause ->
                ProcessError(onDismissStatisticError, cause)
            }
        }
    }
}