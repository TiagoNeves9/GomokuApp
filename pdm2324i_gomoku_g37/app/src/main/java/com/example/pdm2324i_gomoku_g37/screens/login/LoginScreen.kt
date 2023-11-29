package com.example.pdm2324i_gomoku_g37.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.helpers.LoginScreenTestTags.LoginScreenTestTag
import com.example.pdm2324i_gomoku_g37.screens.components.BUTTON_RADIUS
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.components.PasswordTextFieldView
import com.example.pdm2324i_gomoku_g37.screens.components.UsernameTextFieldView
import com.example.pdm2324i_gomoku_g37.ui.theme.BackgroundBlue
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme


data class LoginScreenState(
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
)

data class LoginScreenFunctions(
    val onUsernameChange: (String) -> Unit = { },
    val onPasswordChange: (String) -> Unit = { },
    val onPasswordVisibilityChange: () -> Unit = { },
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    state: LoginScreenState = LoginScreenState(),
    navigation: NavigationHandlers = NavigationHandlers(),
    functions: LoginScreenFunctions = LoginScreenFunctions(),
    onHomeRequested: () -> Unit = { },
    onSignUpRequested: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag(LoginScreenTestTag),
        topBar = {
            CustomBar(
                text = stringResource(R.string.activity_login_top_bar_title),
                navigation = navigation
            )
        },
        bottomBar = { GroupFooterView(Color.White) }
    ) { padding ->
        CustomContainerView(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.img_gomoku_icon),
                contentDescription = null
            )

            UsernameTextFieldView(value = state.username, onClick = functions.onUsernameChange)

            PasswordTextFieldView(
                value = state.password,
                passwordVisibility = state.passwordVisible,
                //enablePasswordVisibility = functions.onPasswordVisibilityChange,
                isRepeatPassword = false,
                onClick = functions.onPasswordChange
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                ElevatedButton(
                    onClick = onHomeRequested,
                    shape = RoundedCornerShape(BUTTON_RADIUS),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 35.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.activity_login_top_bar_title),
                        color = BackgroundBlue
                    )
                }
                ElevatedButton(
                    onClick = onSignUpRequested,
                    shape = RoundedCornerShape(BUTTON_RADIUS),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 35.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.activity_sign_up_bar_title),
                        color = BackgroundBlue
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginPreview() {
    GomokuTheme {
        LoginScreen()
    }
}

