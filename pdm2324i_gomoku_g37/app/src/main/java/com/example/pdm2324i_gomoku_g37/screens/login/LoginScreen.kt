package com.example.pdm2324i_gomoku_g37.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.helpers.LoginScreenTestTags.LoginScreenTestTag
import com.example.pdm2324i_gomoku_g37.screens.components.BUTTON_RADIUS
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_SCREEN_DEFAULT_PADDING
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_SCREEN_SPACING_PADDING
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.components.PasswordTextFieldView
import com.example.pdm2324i_gomoku_g37.screens.components.UsernameTextFieldView
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
    onHomeRequested: () -> Unit = { }
) =
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
        bottomBar = { GroupFooterView() }
    ) { padding ->
        CustomContainerView(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.img_gomoku_icon),
                contentDescription = null
            )

            UsernameTextFieldView(state.username, functions.onUsernameChange)

            PasswordTextFieldView(
                value = state.password,
                passwordVisibility = state.passwordVisible,
                enablePasswordVisibility = functions.onPasswordVisibilityChange,
                isRepeatPassword = false,
                onClick = functions.onPasswordChange
            )

            ElevatedButton(
                onClick = onHomeRequested,
                shape = RoundedCornerShape(BUTTON_RADIUS),
                contentPadding = PaddingValues(
                    start = MAIN_SCREEN_DEFAULT_PADDING,
                    end = MAIN_SCREEN_DEFAULT_PADDING,
                    top = MAIN_SCREEN_SPACING_PADDING,
                    bottom = MAIN_SCREEN_SPACING_PADDING
                ),
                modifier = Modifier.padding(padding)
            ) {
                Text("Login")
            }
        }
    }

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginPreview() =
    GomokuTheme {
        LoginScreen()
    }
