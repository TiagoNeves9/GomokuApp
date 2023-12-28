package com.example.pdm2324i_gomoku_g37.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.helpers.HomeScreenTestTags.HomeScreenDisplayTestTag
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.HOME_FONT_SIZE
import com.example.pdm2324i_gomoku_g37.screens.components.HOME_USERNAME_FONT_SIZE
import com.example.pdm2324i_gomoku_g37.screens.components.LargeCustomTitleView
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.components.ProcessError
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme

private data class MenuOption(
    val onOptionSelected: () -> Unit = { },
    val imageVector: ImageVector,
    val text: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userInfo: UserInfo,
    navigation: NavigationHandlers = NavigationHandlers(),
    error: Exception? = null,
    onAuthorsRequested: () -> Unit = { },
    onPlayRequested: () -> Unit = { },
    onRankingsRequested: () -> Unit = { },
    onProfileRequest: () -> Unit = { },
    onLogoutRequested: () -> Unit = { },
    onDismissError: () -> Unit = { }
) {
    val menu: List<MenuOption> = listOf(
        MenuOption(onPlayRequested, Icons.Default.PlayArrow, stringResource(id = R.string.menu_play_option)),
        MenuOption(onRankingsRequested, Icons.Default.Star, stringResource(id = R.string.menu_rankings_option)),
        MenuOption(onAuthorsRequested, Icons.Default.Face, stringResource(id = R.string.menu_authors_option)),
        MenuOption(onProfileRequest, Icons.Default.PermIdentity, stringResource(id = R.string.menu_profile_option))
    )

    Scaffold(
        topBar = { CustomBar(text = stringResource(R.string.activity_home_top_bar_title), navigation = navigation) },
        bottomBar = { GroupFooterView() },
        modifier = Modifier.testTag(HomeScreenDisplayTestTag)
    ) { padding ->
        CustomContainerView(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(text = userInfo.username, fontSize = HOME_USERNAME_FONT_SIZE)

            Button(onClick = onLogoutRequested, Modifier) {
                val logoutStr = stringResource(id = R.string.menu_logout_option)
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = logoutStr
                )
                Text(logoutStr)
            }

            LargeCustomTitleView(text = stringResource(R.string.activity_menu_title))

            val menuGroups = menu.chunked(2)

            for (menuGroup in menuGroups) {
                Row(Modifier, Arrangement.Center) {
                    menuGroup.forEach { menuOption ->
                        MenuButton(menuOption.onOptionSelected) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = menuOption.imageVector,
                                    contentDescription = menuOption.text
                                )
                                Text(
                                    text = menuOption.text,
                                    fontSize = HOME_FONT_SIZE,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            error?.cause?.let { cause ->
                ProcessError(onDismissError, cause)
            }
        }
    }
}

@Composable
fun MenuButton(onClick: () -> Unit = {}, content: @Composable () -> Unit) =
    ElevatedButton(
        shape = RoundedCornerShape(2.dp),
        onClick = onClick,
        modifier = Modifier.size(110.dp).padding(2.dp)
    ) {
        content()
    }


@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun MenuButtonPreview() = MenuButton { Text(text = "Play") }

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreenPreview() =
    GomokuTheme {
        HomeScreen(UserInfo("1","prefiew user", "ab12"), NavigationHandlers({}, {}))
    }