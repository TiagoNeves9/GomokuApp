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
    val usernameErrorText: String = "",
    val isUsernameInputError: Boolean = false,
    val password: String = "",
    val passwordErrorText: String = "",
    val isPasswordInputError: Boolean = false,
    val passwordVisible: Boolean = false,
    val confirmPassword: String = "",
    val confirmPasswordErrorText: String = "",
    val isConfirmPasswordInputError: Boolean = false,
    val confirmPasswordVisible: Boolean = false
)

data class SignUpScreenFunctions(
    val onUsernameChange: (String) -> Unit = { },
    val onUsernameErrorTextChange: (String) -> Unit = { },
    val onIsUsernameInputErrorChange: (Boolean) -> Unit = { },
    val onPasswordChange: (String) -> Unit = { },
    val onPasswordErrorTextChange: (String) -> Unit = { },
    val onIsPasswordInputErrorChange: (Boolean) -> Unit = { },
    val onPasswordVisibilityChange: (Boolean) -> Unit = { },
    val onConfirmPasswordChange: (String) -> Unit = { },
    val onConfirmPasswordErrorTextChange: (String) -> Unit = { },
    val onIsConfirmPasswordInputErrorChange: (Boolean) -> Unit = {},
    val onConfirmPasswordVisibilityChange: (Boolean) -> Unit = { },
    val onSignUpRequested: () -> Unit = { },
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

            UsernameTextFieldView(
                value = state.username,
                isError = state.isUsernameInputError,
                errorText = state.usernameErrorText,
                onClick = functions.onUsernameChange
            )

            PasswordTextFieldView(
                value = state.password,
                isError = state.isPasswordInputError,
                errorText = state.passwordErrorText,
                passwordVisibility = state.passwordVisible,
                enablePasswordVisibility = functions.onPasswordVisibilityChange,
                isRepeatPassword = false,
                onClick = functions.onPasswordChange
            )

            PasswordTextFieldView(
                value = state.confirmPassword,
                isError = state.isConfirmPasswordInputError,
                errorText = state.confirmPasswordErrorText,
                passwordVisibility = state.confirmPasswordVisible,
                enablePasswordVisibility = functions.onConfirmPasswordVisibilityChange,
                isRepeatPassword = true,
                onClick = functions.onConfirmPasswordChange
            )

            ElevatedButton(
                onClick = functions.onSignUpRequested,
                enabled = validateUserInfo(state, functions),
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
private fun validateUserInfo(
    state: SignUpScreenState = SignUpScreenState(),
    functions: SignUpScreenFunctions = SignUpScreenFunctions()
): Boolean = when {
    state.username.isBlank() -> {
        functions.onUsernameErrorTextChange(stringResource(R.string.username_is_blank_input_error))
        functions.onIsUsernameInputErrorChange(true)
        false
    }
    state.password.isBlank() -> {
       functions.onIsUsernameInputErrorChange(false)
       functions.onPasswordErrorTextChange(stringResource(R.string.password_is_blank_input_error))
       functions.onIsPasswordInputErrorChange(true)
       false
    }
    state.confirmPassword.isBlank() -> {
        functions.onIsUsernameInputErrorChange(false)
        functions.onIsPasswordInputErrorChange(false)
        functions.onConfirmPasswordErrorTextChange(stringResource(R.string.repeat_password_is_blank_input_error))
        functions.onIsConfirmPasswordInputErrorChange(true)
        false
    }
    state.password != state.confirmPassword -> {
        functions.onIsUsernameInputErrorChange(false)
        functions.onIsPasswordInputErrorChange(false)
        functions.onIsConfirmPasswordInputErrorChange(true)
        functions.onConfirmPasswordErrorTextChange(stringResource(R.string.repeat_password_and_password_not_equal_input_error))
        false
    }
    else -> {
        functions.onIsUsernameInputErrorChange(false)
        functions.onIsPasswordInputErrorChange(false)
        functions.onIsConfirmPasswordInputErrorChange(false)
        true
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SignUpPreview() =
    GomokuTheme {
        SignUpScreen()
    }