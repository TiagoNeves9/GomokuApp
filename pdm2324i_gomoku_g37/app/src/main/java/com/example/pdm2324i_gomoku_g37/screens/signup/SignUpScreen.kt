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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pdm2324i_gomoku_g37.R
import com.example.pdm2324i_gomoku_g37.helpers.SignUpScreenTestTags.SignUpScreenTestTag
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navigation: NavigationHandlers = NavigationHandlers(),
    onHomeRequested: () -> Unit = { }
){
    var textUsername by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }
    var textRepeatPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var passwordRepeatVisibility by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CustomBar(text = stringResource(id = R.string.activity_sign_up_bar_title), navigation)
        },
        bottomBar = { GroupFooterView() },
        modifier = Modifier
            .fillMaxSize()
            .testTag(SignUpScreenTestTag),
    ) { padding ->
        CustomContainerView(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_gomoku_icon),
                contentDescription = null
            )

            UsernameTextFieldView(textUsername) { textUsername = it }

            PasswordTextFieldView(
                value = textPassword,
                passwordVisibility = passwordVisibility,
                enablePasswordVisibility = { passwordVisibility = !passwordVisibility },
                isRepeatPassword = false
            ) {
                textPassword = it
            }

            PasswordTextFieldView(
                value = textRepeatPassword,
                passwordVisibility = passwordRepeatVisibility,
                enablePasswordVisibility = { passwordRepeatVisibility = !passwordRepeatVisibility },
                isRepeatPassword = true
            ) {
                textRepeatPassword = it
            }

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
                    text = stringResource(id = R.string.activity_sign_up_register_button)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SignUpPreview() =
    GomokuTheme {
        SignUpScreen()
    }