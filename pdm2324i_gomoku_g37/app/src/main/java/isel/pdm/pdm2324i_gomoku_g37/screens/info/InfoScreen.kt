package isel.pdm.pdm2324i_gomoku_g37.screens.info

import android.util.Log
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
import isel.pdm.pdm2324i_gomoku_g37.helpers.InfoScreenTestTags.InfoFetchInfoErrorTag
import isel.pdm.pdm2324i_gomoku_g37.screens.components.CustomBar
import isel.pdm.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import isel.pdm.pdm2324i_gomoku_g37.screens.components.DEFAULT_CONTENT_PADDING
import isel.pdm.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import isel.pdm.pdm2324i_gomoku_g37.screens.components.LoadingAlert
import isel.pdm.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import isel.pdm.pdm2324i_gomoku_g37.screens.components.ProcessError
import isel.pdm.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import isel.pdm.pdm2324i_gomoku_g37.R
import isel.pdm.pdm2324i_gomoku_g37.domain.Loading
import isel.pdm.pdm2324i_gomoku_g37.domain.exceptionOrNull
import isel.pdm.pdm2324i_gomoku_g37.domain.getOrNull
import isel.pdm.pdm2324i_gomoku_g37.domain.idle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    info: isel.pdm.pdm2324i_gomoku_g37.domain.LoadState<String?> = idle(),
    navigation: NavigationHandlers = NavigationHandlers(),
    onDismiss: () -> Unit = { }
) {
    Scaffold(
        topBar = {
            CustomBar(
                text = stringResource(id = R.string.activity_info_top_bar_title),
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
            val textModifier = Modifier
                .fillMaxWidth()
                .padding(DEFAULT_CONTENT_PADDING)

            info.getOrNull()?.let { apiInfo ->
                Text(
                    text = apiInfo,
                    modifier = textModifier,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = "O Gomoku, também conhecido como Gobang, \n" +
                        "é um jogo de tabuleiro estratégico\n" +
                        "tradicionalmente jogado com peças de\n" +
                        "Go em um tabuleiro de Go ligeiramente\n" +
                        "modificado. No entanto, ao contrario deste,\n" +
                        "uma vez que as peças são colocadas não podem\n" +
                        "ser movidas ou retiradas do tabuleiro.",
                modifier = textModifier,
                textAlign = TextAlign.Center
            )

            if (info is Loading)
                LoadingAlert(
                    R.string.loading_api_info_title, R.string.loading_api_info_message
                )

            info.exceptionOrNull()?.let { cause ->
                cause.message?.let { Log.v(InfoFetchInfoErrorTag, it) }
                ProcessError(onDismiss, cause)
            }
        }
    }
}

@Preview
@Composable
fun InfoScreenPreview() {
    val info =
        "The Gomoku application is in version X.Y.Z\n and was made by Group 37 - Class 53D"
    GomokuTheme {
        InfoScreen(
            info = isel.pdm.pdm2324i_gomoku_g37.domain.loaded(Result.success(info)),
            navigation = NavigationHandlers(onBackRequested = { })
        )
    }
}