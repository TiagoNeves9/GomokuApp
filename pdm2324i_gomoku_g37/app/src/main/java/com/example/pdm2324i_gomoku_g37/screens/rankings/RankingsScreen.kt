package com.example.pdm2324i_gomoku_g37.screens.rankings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.Loading
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
fun RankingsScreen(
    rankings: LoadState<List<UserStatistics>> = idle(),
    onDismissError: () -> Unit = { },
    navigation: NavigationHandlers = NavigationHandlers()
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CustomBar(
                text = stringResource(R.string.activity_rankings_top_bar_title),
                navigation = navigation
            )
        },
        bottomBar = { GroupFooterView() }
    ) { padding ->
        CustomContainerView(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val rankingsList = rankings.getOrNull()

            if (rankingsList != null) {
                rankingsList.forEach { userStatistics ->
                    UserStatsView(userStatistics)
                }
            } else {
                Text(stringResource(id = R.string.rankings_no_rankings_found))
            }

            if (rankings is Loading)
                LoadingAlert(R.string.loading_rankings_title, R.string.loading_rankings_message)

            rankings.exceptionOrNull()?.let { cause ->
                ProcessError(onDismissError, cause)
            }
        }
    }
}

@Composable
private fun UserStatsView(userStatistics: UserStatistics) {
        Text(
            text = stringResource(id = R.string.username_text).plus(" ${userStatistics.user}"),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.profile_total_score).plus(" ${userStatistics.score }"),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.profile_screen_total_games).plus(" ${userStatistics.ngames}"),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
}



@Preview
@Composable
fun RankingsScreenPreview() = RankingsScreen()