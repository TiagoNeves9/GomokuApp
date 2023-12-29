package com.example.pdm2324i_gomoku_g37.screens.main

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.pdm2324i_gomoku_g37.helpers.MainScreenTestTags.StartButtonTestTag
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class MainScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun main_screen_displays_start_button() {
        // Arrange
        composeTestRule.setContent {
            MainScreen(onStartRequested = { })
        }
        // Act
        // Assert
        composeTestRule.onNodeWithTag(StartButtonTestTag).assertExists()
        composeTestRule.onNodeWithTag(StartButtonTestTag).assertIsEnabled()
    }

    @Test
    fun pressing_play_calls_onPlayRequested_callback() {
        // Arrange
        var playRequested = false
        composeTestRule.setContent {
            MainScreen(onStartRequested = { playRequested = true })
        }
        // Act
        composeTestRule.onNodeWithTag(StartButtonTestTag).performClick()
        // Assert
        assertTrue(playRequested)
    }
}