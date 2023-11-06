package com.example.pdm2324i_gomoku_g37.screens.signup

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
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.Loading
import com.example.pdm2324i_gomoku_g37.domain.UserId
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.helpers.SignUpScreenTestTags.SignUpScreenTestTag
import com.example.pdm2324i_gomoku_g37.screens.components.BUTTON_RADIUS
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_SCREEN_DEFAULT_PADDING
import com.example.pdm2324i_gomoku_g37.screens.components.MAIN_SCREEN_SPACING_PADDING
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.components.PasswordTextFieldView
import com.example.pdm2324i_gomoku_g37.screens.components.RefreshFab
import com.example.pdm2324i_gomoku_g37.screens.components.UsernameTextFieldView
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme


data class SignUpScreenState(
    val user: LoadState<UserId> = idle(),
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val confirmPassword: String = "",
    val confirmPasswordVisible: Boolean = false
)

data class SignUpScreenFunctions(
    val onUsernameChange: (String) -> Unit = { },
    val onPasswordChange: (String) -> Unit = { },
    val onPasswordVisibilityChange: () -> Unit = { },
    val onConfirmPasswordChange: (String) -> Unit = { },
    val onConfirmPasswordVisibilityChange: () -> Unit = { },
    val onSignUpRequested: () -> Unit = {},
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    state: SignUpScreenState = SignUpScreenState(),
    navigation: NavigationHandlers = NavigationHandlers(),
    functions: SignUpScreenFunctions = SignUpScreenFunctions()
) =
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag(SignUpScreenTestTag),
        floatingActionButton = {
            RefreshFab(
                onClick = functions.onSignUpRequested,
                refreshing = state.user is Loading,
                modifier = Modifier.testTag("SignUpRefreshTestTag")
            )
        },
        topBar = {
            CustomBar(
                text = stringResource(R.string.activity_sign_up_bar_title),
                navigation = navigation
            )
        },
        bottomBar = { GroupFooterView() },
    ) { padding ->
        CustomContainerView(
            Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_gomoku_icon),
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

            PasswordTextFieldView(
                value = state.confirmPassword,
                passwordVisibility = state.confirmPasswordVisible,
                enablePasswordVisibility = functions.onConfirmPasswordVisibilityChange,
                isRepeatPassword = true,
                onClick = functions.onConfirmPasswordChange
            )

            ElevatedButton(
                onClick = functions.onSignUpRequested,
                shape = RoundedCornerShape(BUTTON_RADIUS),
                contentPadding = PaddingValues(
                    start = MAIN_SCREEN_DEFAULT_PADDING,
                    end = MAIN_SCREEN_DEFAULT_PADDING,
                    top = MAIN_SCREEN_SPACING_PADDING,
                    bottom = MAIN_SCREEN_SPACING_PADDING
                ),
                modifier = Modifier.padding(padding)
            ) {
                Text(stringResource(R.string.activity_sign_up_register_button))
            }
        }
    }

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SignUpPreview() =
    GomokuTheme {
        SignUpScreen()
    }