package isel.pdm.pdm2324i_gomoku_g37.screens.rankings

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
import isel.pdm.pdm2324i_gomoku_g37.R
import isel.pdm.pdm2324i_gomoku_g37.domain.LoadState
import isel.pdm.pdm2324i_gomoku_g37.domain.Loading
import isel.pdm.pdm2324i_gomoku_g37.domain.UserStatistics
import isel.pdm.pdm2324i_gomoku_g37.domain.exceptionOrNull
import isel.pdm.pdm2324i_gomoku_g37.domain.getOrNull
import isel.pdm.pdm2324i_gomoku_g37.domain.idle
import isel.pdm.pdm2324i_gomoku_g37.screens.components.CustomBar
import isel.pdm.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import isel.pdm.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import isel.pdm.pdm2324i_gomoku_g37.screens.components.LargeCustomTitleView
import isel.pdm.pdm2324i_gomoku_g37.screens.components.LoadingAlert
import isel.pdm.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import isel.pdm.pdm2324i_gomoku_g37.screens.components.ProcessError


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
                LargeCustomTitleView("Best players:")
                rankingsList.forEach { userStatistics ->
                    UserStatsView(userStatistics)
                }
            } else Text(stringResource(R.string.rankings_no_rankings_found))

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
    Text(text = "\n")
    Text(
        text = stringResource(R.string.username_text)
            .plus(" ${userStatistics.user}"),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
    Text(
        text = stringResource(R.string.profile_total_score)
            .plus(" ${userStatistics.score}"),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
    Text(
        text = stringResource(R.string.profile_screen_total_games)
            .plus(" ${userStatistics.ngames}"),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
}


@Preview
@Composable
fun RankingsScreenPreview() = RankingsScreen()