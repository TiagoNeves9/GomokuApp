package com.example.pdm2324i_gomoku_g37.screens.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.Loading
import com.example.pdm2324i_gomoku_g37.domain.UserId
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.exceptionOrNull
import com.example.pdm2324i_gomoku_g37.domain.idle
import com.example.pdm2324i_gomoku_g37.helpers.SignUpScreenTestTags.SignUpScreenTestTag
import com.example.pdm2324i_gomoku_g37.screens.components.BUTTON_RADIUS
import com.example.pdm2324i_gomoku_g37.screens.components.CustomBar
import com.example.pdm2324i_gomoku_g37.screens.components.CustomContainerView
import com.example.pdm2324i_gomoku_g37.screens.components.GroupFooterView
import com.example.pdm2324i_gomoku_g37.screens.components.LoadingAlert
import com.example.pdm2324i_gomoku_g37.screens.components.NavigationHandlers
import com.example.pdm2324i_gomoku_g37.screens.components.PasswordTextFieldView
import com.example.pdm2324i_gomoku_g37.screens.components.ProcessError
import com.example.pdm2324i_gomoku_g37.screens.components.UsernameTextFieldView
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme


val myPadding = 10.dp

data class SignUpScreenState(
    val userInfo: LoadState<UserId> = idle(),
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
    val onIsConfirmPasswordInputErrorChange: (Boolean) -> Unit = { },
    val onConfirmPasswordVisibilityChange: (Boolean) -> Unit = { },
    val onSignUpRequested: () -> Unit = { },
    val onDismissError: () -> Unit = { }
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
        topBar = {
            CustomBar(
                text = stringResource(R.string.activity_sign_up_bar_title),
                navigation = navigation
            )
        },
        bottomBar = { GroupFooterView() },
    ) { padding ->
        CustomContainerView(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                Modifier.fillMaxHeight(0.3F),
                Arrangement.Center,
                Alignment.Top
            ) {
                Image(
                    painter = painterResource(R.drawable.img_gomoku_icon),
                    contentDescription = null
                )
            }

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
                enabled = isButtonEnabled(state),
                shape = RoundedCornerShape(BUTTON_RADIUS),
                modifier = Modifier.padding(myPadding)
            ) {
                Text(stringResource(R.string.activity_sign_up_register_button))
            }

            if (state.userInfo is Loading)
                LoadingAlert(R.string.loading_sign_up_title, R.string.loading_sign_up_message)

            ValidateSignUpInputs(state, functions)

            state.userInfo.exceptionOrNull()?.let { cause ->
                ProcessError(functions.onDismissError, cause)
            }
        }
    }


private fun isButtonEnabled(state: SignUpScreenState = SignUpScreenState()) =
    state.username.isNotBlank() && state.password.isNotBlank() &&
            state.confirmPassword.isNotBlank() && state.password == state.confirmPassword

@Composable
private fun ValidateSignUpInputs(state: SignUpScreenState, functions: SignUpScreenFunctions) =
    when {
        state.username.isBlank() -> IncorrectUsername(
            functions,
            R.string.username_is_blank_input_error
        )

        state.password.isBlank() -> IncorrectPassword(
            functions,
            R.string.password_is_blank_input_error
        )

        state.confirmPassword.isBlank() -> IncorrectConfirmPassword(
            functions,
            R.string.repeat_password_is_blank_input_error
        )

        state.password != state.confirmPassword -> IncorrectConfirmPassword(
            functions,
            R.string.repeat_password_and_password_not_equal_input_error
        )

        else -> clearInputsError(functions)
    }

@Composable
private fun IncorrectUsername(functions: SignUpScreenFunctions, message: Int) {
    functions.onUsernameErrorTextChange(stringResource(message))
    functions.onIsUsernameInputErrorChange(true)
}

@Composable
private fun IncorrectPassword(functions: SignUpScreenFunctions, message: Int) {
    functions.onIsUsernameInputErrorChange(false)
    functions.onIsPasswordInputErrorChange(true)
    functions.onPasswordErrorTextChange(stringResource(message))
}

@Composable
private fun IncorrectConfirmPassword(functions: SignUpScreenFunctions, message: Int) {
    functions.onIsUsernameInputErrorChange(false)
    functions.onIsPasswordInputErrorChange(false)
    functions.onIsConfirmPasswordInputErrorChange(true)
    functions.onConfirmPasswordErrorTextChange(stringResource(message))
}

private fun clearInputsError(functions: SignUpScreenFunctions) {
    functions.onIsUsernameInputErrorChange(false)
    functions.onIsPasswordInputErrorChange(false)
    functions.onIsConfirmPasswordInputErrorChange(false)
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SignUpPreview() =
    GomokuTheme {
        SignUpScreen()
    }