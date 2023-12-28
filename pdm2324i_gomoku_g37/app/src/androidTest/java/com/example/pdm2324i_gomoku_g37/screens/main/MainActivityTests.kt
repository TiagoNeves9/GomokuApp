package com.example.pdm2324i_gomoku_g37.screens.main

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import com.example.pdm2324i_gomoku_g37.GomokuTestApplication
import com.example.pdm2324i_gomoku_g37.helpers.HomeScreenTestTags.HomeScreenDisplayTestTag
import com.example.pdm2324i_gomoku_g37.helpers.LoginScreenTestTags.LoginScreenTestTag
import com.example.pdm2324i_gomoku_g37.helpers.MainScreenTestTags.MainScreenDisplayTestTag
import com.example.pdm2324i_gomoku_g37.helpers.MainScreenTestTags.StartButtonTestTag
import com.example.pdm2324i_gomoku_g37.screens.components.ErrorAlertTestTag
import com.example.pdm2324i_gomoku_g37.utils.PreserveDefaultDependenciesNoActivity
import com.example.pdm2324i_gomoku_g37.utils.createPreserveDefaultDependenciesComposeRuleNoActivity
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class MainActivityTests {

    @get:Rule
    val testRule = createPreserveDefaultDependenciesComposeRuleNoActivity()

    /**
     * Shortcut to the [GomokuTestApplication] instance, used to access the dependencies.
     */
    private val testApplication by lazy {
        (testRule.activityRule as PreserveDefaultDependenciesNoActivity).testApplication
    }


    @Test
    fun initially_the_main_screen_is_displayed() {
        // Arrange
        ActivityScenario.launch(MainActivity::class.java).use {
            // Act
            // Assert
            testRule.onNodeWithTag(MainScreenDisplayTestTag).assertExists()
        }
    }

    @Test
    fun pressing_start_navigates_to_home_if_user_info_exists() {
        // Arrange
        ActivityScenario.launch(MainActivity::class.java).use {
            // Act
            testRule.onNodeWithTag(StartButtonTestTag).performClick()
            testRule.waitForIdle()
            // Assert
            testRule.onNodeWithTag(HomeScreenDisplayTestTag).assertExists()
        }
    }

    @Test
    fun pressing_start_navigates_to_login_if_user_info_does_not_exist() {
        // Arrange
        testApplication.userInfoRepository = mockk {
            coEvery { getUserInfo() } returns null
        }

        ActivityScenario.launch(MainActivity::class.java).use {
            // Act
            testRule.onNodeWithTag(StartButtonTestTag).performClick()
            testRule.waitForIdle()
            // Assert
            testRule.onNodeWithTag(LoginScreenTestTag).assertExists()
        }
    }

    @Test
    fun pressing_start_navigates_to_home_if_user_info_exists_regardless_of_reconfigurations() {
        // Arrange
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            // Act
            testRule.onNodeWithTag(StartButtonTestTag).performClick()
            scenario.onActivity { it.recreate() }
            // Assert
            testRule.onNodeWithTag(HomeScreenDisplayTestTag).assertExists()
        }
    }

    @Test
    fun an_error_message_is_displayed_if_an_error_occurs_while_loading_user_info() {
        // Arrange
        testApplication.userInfoRepository = mockk {
            coEvery { getUserInfo() } throws Exception()
        }

        ActivityScenario.launch(MainActivity::class.java).use {
            // Act
            testRule.onNodeWithTag(StartButtonTestTag).performClick()
            testRule.waitForIdle()
            // Assert
            testRule.onNodeWithTag(ErrorAlertTestTag).assertExists()
        }
    }
}