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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.Loading
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.exceptionOrNull
import com.example.pdm2324i_gomoku_g37.domain.getOrNull
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.domain.loaded
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.LargeCustomTitleView
import com.example.pdm2324i_gomoku_g37.screens.components.LoadingAlert
import com.example.pdm2324i_gomoku_g37.screens.components.ProcessError
import com.example.pdm2324i_gomoku_g37.service.GomokuUsers
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme


val BUTTON_NAME_SIZE = 12.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userInfo: LoadState<UserInfo?> = idle(),
    onAuthorsRequested: () -> Unit = { },
    onPlayRequested: () -> Unit = { },
    onRankingsRequested: () -> Unit = { },
    onAboutRequested: () -> Unit = { },
    onDismissError: () -> Unit = {}
) {
    Scaffold(

        bottomBar = { GroupFooterView(Color.White) }
    ) { padding ->
        CustomContainerView(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            if (userInfo is Loading)
                LoadingAlert(R.string.loading_home_title, R.string.loading_home_message)

            userInfo.exceptionOrNull()?.let { cause ->
                ProcessError(onDismissError, cause)
            }

            userInfo.getOrNull()?.let { user ->
                Text("User: ${user.username}")
            }

            LargeCustomTitleView(text = stringResource(id = R.string.activity_menu_title))

            Row(
                Modifier,
                Arrangement.Center
            ) {
                MenuButton(onPlayRequested) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play"
                        )
                        Text(
                            text = "Play",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                MenuButton(onRankingsRequested) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rankings"
                        )
                        Text(
                            text = "Rankings",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Row(
                Modifier,
                Arrangement.Center
            ) {
                MenuButton(onAuthorsRequested) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Face,
                            contentDescription = "Authors"
                        )
                        Text(
                            text = "Authors",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                MenuButton(onAboutRequested) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About"
                        )
                        Text(
                            text = "About",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MenuButton(onClick: () -> Unit = {}, content: @Composable () -> Unit) =
    ElevatedButton(
        shape = RoundedCornerShape(2.dp),
        onClick = onClick,
        //contentPadding = PaddingValues(30.dp)
        modifier = Modifier
            .size(110.dp)
            .padding(2.dp)
    ) {
        content()
    }

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun MenuButtonPreview() = MenuButton { Text(text = "Play") }

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreenPreview() {
    GomokuTheme {
        val userInfo: LoadState<UserInfo?> = loaded(Result.success(UserInfo("1", "prefiew user", "ab12")))
        HomeScreen(userInfo)
    }
}
