package isel.pdm.pdm2324i_gomoku_g37.screens.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import isel.pdm.pdm2324i_gomoku_g37.domain.UserInfo
import isel.pdm.pdm2324i_gomoku_g37.screens.home.HomeActivity
import isel.pdm.pdm2324i_gomoku_g37.screens.signup.SignUpActivity
import isel.pdm.pdm2324i_gomoku_g37.GomokuDependenciesContainer
import isel.pdm.pdm2324i_gomoku_g37.domain.getOrNull
import isel.pdm.pdm2324i_gomoku_g37.domain.idle
import isel.pdm.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import kotlinx.coroutines.launch


class LoginActivity : ComponentActivity() {
    private val dependencies by lazy { application as GomokuDependenciesContainer }

    private val viewModel by viewModels<LoginScreenViewModel> {
        LoginScreenViewModel.factory(dependencies.gomokuService, dependencies.userInfoRepository)
    }

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, LoginActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)

        lifecycleScope.launch {
            viewModel.userInfoFlow.collect {
                if (it is isel.pdm.pdm2324i_gomoku_g37.domain.Loaded)
                    doNavigation(it.getOrNull())
            }
        }

        setContent {
            val currentUserInfo by viewModel.userInfoFlow.collectAsState(initial = idle())
            GomokuTheme {
                LoginScreen(
                    state = LoginScreenState(
                        userInfo = currentUserInfo,
                        username = viewModel.username,
                        usernameErrorText = viewModel.usernameErrorText,
                        isUsernameInputError = viewModel.isUsernameInputError,
                        password = viewModel.password,
                        passwordErrorText = viewModel.passwordErrorText,
                        isPasswordInputError = viewModel.isPasswordInputError,
                        passwordVisible = viewModel.isPasswordVisible
                    ),
                    onSignUpRequested = { SignUpActivity.navigateTo(origin = this) },
                    functions = LoginScreenFunctions(
                        onUsernameChange = viewModel::changeUsername,
                        onUsernameErrorTextChange = viewModel::changeUsernameErrorText,
                        onIsUsernameInputErrorChange = viewModel::changeIsUsernameInputError,
                        onPasswordChange = viewModel::changePassword,
                        onPasswordErrorTextChange = viewModel::changePasswordErrorText,
                        onIsPasswordInputErrorChange = viewModel::changeIsPasswordInputError,
                        onPasswordVisibilityChange = viewModel::changeIsPasswordVisible,
                        onLoginRequested = viewModel::signIn,
                        onDismissError = viewModel::resetToIdle
                    )
                )
            }
        }
    }

    private fun doNavigation(userInfo: UserInfo?) {
        if (userInfo != null) {
            HomeActivity.navigateTo(this, userInfo = userInfo)
            finish()
        }
    }
}