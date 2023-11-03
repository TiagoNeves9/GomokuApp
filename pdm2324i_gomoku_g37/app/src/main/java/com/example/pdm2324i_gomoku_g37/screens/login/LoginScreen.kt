package com.example.pdm2324i_gomoku_g37.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.helpers.LoginScreenTestTags.LoginScreenTestTag
import com.example.pdm2324i_gomoku_g37.screens.components.BUTTON_DEFAULT_PADDING
import com.example.pdm2324i_gomoku_g37.screens.components.BUTTON_RADIUS
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.DEFAULT_CONTENT_PADDING
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.LARGE_BUTTON_HEIGHT
import com.example.pdm2324i_gomoku_g37.screens.components.LARGE_BUTTON_WIDTH
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_SCREEN_DEFAULT_PADDING
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_SCREEN_SPACING_PADDING
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.components.PasswordTextFieldView
import com.example.pdm2324i_gomoku_g37.screens.components.PasswordVisibilityView
import com.example.pdm2324i_gomoku_g37.screens.components.UsernameTextFieldView
import com.example.pdm2324i_gomoku_g37.screens.components.onVisualTransformation
import com.example.pdm2324i_gomoku_g37.screens.signup.SignUpScreenFunctions
import com.example.pdm2324i_gomoku_g37.screens.signup.SignUpScreenState
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
fun LoginScreen (
    state: LoginScreenState = LoginScreenState(),
    navigation: NavigationHandlers = NavigationHandlers(),
    functions: LoginScreenFunctions = LoginScreenFunctions(),
    onHomeRequested: () -> Unit = { }
) {
    Scaffold(
        topBar = {
            CustomBar(text = stringResource(id = R.string.activity_login_top_bar_title), navigation)
        },
        bottomBar = { GroupFooterView() },
        modifier = Modifier
            .fillMaxSize()
            .testTag(LoginScreenTestTag)
    ) { padding ->
        CustomContainerView(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_gomoku_icon),
                contentDescription = null
            )
            
            UsernameTextFieldView(state.username,functions.onUsernameChange)

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
                modifier = Modifier
                    .padding(padding)
            ) {
                Text(
                    text = "Login"
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginPreview() =
    GomokuTheme {
        LoginScreen()
    }
