package com.example.pdm2324i_gomoku_g37.screens.login

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ActivityScenario
import com.example.pdm2324i_gomoku_g37.GomokuTestApplication
import com.example.pdm2324i_gomoku_g37.helpers.HomeScreenTestTags
import com.example.pdm2324i_gomoku_g37.helpers.HomeScreenTestTags.HomeScreenDisplayTestTag
import com.example.pdm2324i_gomoku_g37.helpers.LoginScreenTestTags.LoginScreenTestTag
import com.example.pdm2324i_gomoku_g37.helpers.LoginScreenTestTags.PasswordInputTestTag
import com.example.pdm2324i_gomoku_g37.helpers.LoginScreenTestTags.SignUpButtonTestTag
import com.example.pdm2324i_gomoku_g37.helpers.LoginScreenTestTags.UsernameInputTestTag
import com.example.pdm2324i_gomoku_g37.helpers.MainScreenTestTags
import com.example.pdm2324i_gomoku_g37.helpers.SignUpScreenTestTags.SignUpScreenTestTag
import com.example.pdm2324i_gomoku_g37.screens.main.MainActivity
import com.example.pdm2324i_gomoku_g37.utils.PreserveDefaultDependenciesNoActivity
import com.example.pdm2324i_gomoku_g37.utils.SuspendingGate
import com.example.pdm2324i_gomoku_g37.utils.createPreserveDefaultDependenciesComposeRuleNoActivity
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class LoginActivityTests {
    @get:Rule
    val testRule = createPreserveDefaultDependenciesComposeRuleNoActivity()

    /**
     * Shortcut to the [GomokuTestApplication] instance, used to access the dependencies.
     */
    private val testApplication by lazy {
        (testRule.activityRule as PreserveDefaultDependenciesNoActivity).testApplication
    }

    @Test
    fun initially_the_login_screen_is_displayed() {
        // Arrange
        ActivityScenario.launch(LoginActivity::class.java).use {
            // Act
            // Assert
            testRule.onNodeWithTag(LoginScreenTestTag).assertExists()
        }
    }

    @Test
    fun pressing_sign_up_navigates_to_sign_up() {
        // Arrange
        ActivityScenario.launch(LoginActivity::class.java).use {
            // Act
            testRule.onNodeWithTag(SignUpButtonTestTag).performClick()
            testRule.waitForIdle()
            // Assert
            testRule.onNodeWithTag(SignUpScreenTestTag).assertExists()
        }
    }

    @Test
    fun pressing_login_navigates_to_home() {
        // Arrange
        testApplication.userInfoRepository = mockk {
            coEvery { getUserInfo() } returns null
        }

        ActivityScenario.launch(LoginActivity::class.java).use { scenario ->
            // Act
            testRule.onNodeWithTag(UsernameInputTestTag).performTextInput("tbmaster")
            testRule.onNodeWithTag(PasswordInputTestTag).performTextInput("jubas")
            testRule.onNodeWithTag(LoginScreenTestTag).performClick()
            testRule.waitForIdle()
            // Assert
            testRule.onNodeWithTag(SignUpScreenTestTag).assertDoesNotExist()
        }
    }
}