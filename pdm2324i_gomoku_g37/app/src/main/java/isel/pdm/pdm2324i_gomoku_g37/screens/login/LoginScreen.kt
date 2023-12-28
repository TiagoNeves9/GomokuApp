package isel.pdm.pdm2324i_gomoku_g37.screens.login

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import isel.pdm.pdm2324i_gomoku_g37.domain.UserInfo
import isel.pdm.pdm2324i_gomoku_g37.helpers.LoginScreenTestTags.LoginScreenTestTag
import isel.pdm.pdm2324i_gomoku_g37.screens.components.BUTTON_RADIUS
import isel.pdm.pdm2324i_gomoku_g37.screens.components.CustomBar
import isel.pdm.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import isel.pdm.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import isel.pdm.pdm2324i_gomoku_g37.screens.components.LoadingAlert
import isel.pdm.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import isel.pdm.pdm2324i_gomoku_g37.screens.components.PasswordTextFieldView
import isel.pdm.pdm2324i_gomoku_g37.screens.components.ProcessError
import isel.pdm.pdm2324i_gomoku_g37.screens.components.UsernameTextFieldView
import isel.pdm.pdm2324i_gomoku_g37.ui.theme.BackgroundBlue
import isel.pdm.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import isel.pdm.pdm2324i_gomoku_g37.R
import isel.pdm.pdm2324i_gomoku_g37.domain.exceptionOrNull
import isel.pdm.pdm2324i_gomoku_g37.domain.idle


data class LoginScreenState(
    val userInfo: isel.pdm.pdm2324i_gomoku_g37.domain.LoadState<UserInfo?> = idle(),
    val username: String = "",
    val usernameErrorText: String = "",
    val isUsernameInputError: Boolean = false,
    val password: String = "",
    val passwordErrorText: String = "",
    val isPasswordInputError: Boolean = false,
    val passwordVisible: Boolean = false,
)

data class LoginScreenFunctions(
    val onUsernameChange: (String) -> Unit = { },
    val onUsernameErrorTextChange: (String) -> Unit = { },
    val onIsUsernameInputErrorChange: (Boolean) -> Unit = { },
    val onPasswordChange: (String) -> Unit = { },
    val onPasswordErrorTextChange: (String) -> Unit = { },
    val onIsPasswordInputErrorChange: (Boolean) -> Unit = { },
    val onPasswordVisibilityChange: (Boolean) -> Unit = { },
    val onLoginRequested: () -> Unit = { },
    val onDismissError: () -> Unit = { }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    state: LoginScreenState = LoginScreenState(),
    navigation: NavigationHandlers = NavigationHandlers(),
    functions: LoginScreenFunctions = LoginScreenFunctions(),
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
        bottomBar = { GroupFooterView() }
    ) { padding ->
        CustomContainerView(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.img_gomoku_icon),
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

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                ElevatedButton(
                    onClick = functions.onLoginRequested,
                    enabled = isButtonEnabled(state),
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

                if (state.userInfo is isel.pdm.pdm2324i_gomoku_g37.domain.Loading)
                    LoadingAlert(R.string.loading_login_title, R.string.loading_login_message)

                ValidateSignUpInputs(state, functions)

                state.userInfo.exceptionOrNull()?.let { cause ->
                    ProcessError(functions.onDismissError, cause)
                }
            }
        }
    }
}

private fun isButtonEnabled(state: LoginScreenState = LoginScreenState()) =
    state.username.isNotBlank() && state.password.isNotBlank()

@Composable
private fun ValidateSignUpInputs(state: LoginScreenState, functions: LoginScreenFunctions) =
    when {
        state.username.isBlank() -> IncorrectUsername(
            functions,
            R.string.username_is_blank_input_error
        )

        state.password.isBlank() -> IncorrectPassword(
            functions,
            R.string.password_is_blank_input_error
        )

        else -> clearInputsError(functions)
    }

@Composable
private fun IncorrectUsername(functions: LoginScreenFunctions, message: Int) {
    functions.onUsernameErrorTextChange(stringResource(message))
    functions.onIsUsernameInputErrorChange(true)
}

@Composable
private fun IncorrectPassword(functions: LoginScreenFunctions, message: Int) {
    functions.onIsUsernameInputErrorChange(false)
    functions.onIsPasswordInputErrorChange(true)
    functions.onPasswordErrorTextChange(stringResource(message))
}

private fun clearInputsError(functions: LoginScreenFunctions) {
    functions.onIsUsernameInputErrorChange(false)
    functions.onIsPasswordInputErrorChange(false)
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginPreview() =
    GomokuTheme {
        LoginScreen()
    }