package com.example.pdm2324i_gomoku_g37

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorCardTestTag
import com.example.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorEmailButtonTestTag
import com.example.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorNextTestTag
import com.example.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorPrevTestTag
import com.example.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorNoAuthorTestTag
import com.example.pdm2324i_gomoku_g37.screens.authors.AuthorsHandlers
import com.example.pdm2324i_gomoku_g37.screens.authors.AuthorsScreen
import com.example.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class AuthorsScreenTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun screen_initial_state_does_not_display_author() {
        // Arrange
        // Act
        composeTestRule.setContent {
            GomokuTheme {
                AuthorsScreen(
                    index = 0,
                )
            }
        }
        // Assert
        composeTestRule.onNodeWithTag(AuthorCardTestTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(AuthorNoAuthorTestTag).assertExists()
    }

    @Test
    fun screen_initial_state_display_first_author() {
        // Arrange
        val authors: List<Author> = listOf(
            Author(48292, "Tiago Neves", "O melhor programador", "img_tiago", "A48292@alunos.isel.pt"),
        )
        // Act
        composeTestRule.setContent {
            GomokuTheme {
                AuthorsScreen(
                    authors = authors,
                    index = 0,
                )
            }
        }
        // Assert
        composeTestRule.onNodeWithTag(AuthorCardTestTag).assertExists()
    }

    @Test
    fun screen_initial_state_displays_next_button() {
        // Arrange
        val authors: List<Author> = listOf(
            Author(48292, "Tiago Neves", "O melhor programador", "img_tiago", "A48292@alunos.isel.pt"),
        )
        // Act
        composeTestRule.setContent {
            GomokuTheme {
                AuthorsScreen(
                    authors = authors,
                    index = 0,
                    authorsHandlers = AuthorsHandlers(onNextRequested = { })
                )
            }
        }
        // Assert
        composeTestRule.onNodeWithTag(AuthorNextTestTag).assertIsDisplayed()
    }

    @Test
    fun screen_initial_state_displays_prev_button() {
        // Arrange
        val authors: List<Author> = listOf(
            Author(48292, "Tiago Neves", "O melhor programador", "img_tiago", "A48292@alunos.isel.pt"),
        )
        // Act
        composeTestRule.setContent {
            GomokuTheme {
                AuthorsScreen(
                    authors = authors,
                    index = 0,
                    authorsHandlers = AuthorsHandlers(onPrevRequested = { })
                )
            }
        }
        // Assert
        composeTestRule.onNodeWithTag(AuthorPrevTestTag).assertIsDisplayed()
    }

    @Test
    fun click_on_next_calls_onNextRequested() {
        // Arrange
        val authors: List<Author> = listOf(
            Author(48292, "Tiago Neves", "O melhor programador", "img_tiago", "A48292@alunos.isel.pt"),
        )
        var called = false
        // Act
        composeTestRule.setContent {
            GomokuTheme {
                AuthorsScreen(
                    authors = authors,
                    index = 0,
                    authorsHandlers = AuthorsHandlers(onNextRequested = { called = true })
                )
            }
        }
        composeTestRule.onNodeWithTag(AuthorNextTestTag).performClick()
        // Assert
        assertTrue(called)
    }

    @Test
    fun click_on_prev_calls_onPrevRequested() {
        // Arrange
        val authors: List<Author> = listOf(
            Author(48292, "Tiago Neves", "O melhor programador", "img_tiago", "A48292@alunos.isel.pt"),
        )
        var called = false
        // Act
        composeTestRule.setContent {
            GomokuTheme {
                AuthorsScreen(
                    authors = authors,
                    index = 0,
                    authorsHandlers = AuthorsHandlers(onPrevRequested = { called = true })
                )
            }
        }
        composeTestRule.onNodeWithTag(AuthorPrevTestTag).performClick()
        // Assert
        assertTrue(called)
    }

    @Test
    fun screen_initial_state_displays_email_button() {
        // Arrange
        val authors: List<Author> = listOf(
            Author(48292, "Tiago Neves", "O melhor programador", "img_tiago", "A48292@alunos.isel.pt"),
        )
        // Act
        composeTestRule.setContent {
            GomokuTheme {
                AuthorsScreen(
                    authors = authors,
                    index = 0,
                )
            }
        }
        // Assert
        composeTestRule.onNodeWithTag(AuthorEmailButtonTestTag).assertIsDisplayed()
    }

    @Test
    fun click_on_email_calls_onSendEmailRequested() {
        // Arrange
        val authors: List<Author> = listOf(
            Author(48292, "Tiago Neves", "O melhor programador", "img_tiago", "A48292@alunos.isel.pt"),
        )
        var called = false
        // Act
        composeTestRule.setContent {
            GomokuTheme {
                AuthorsScreen(
                    authors = authors,
                    index = 0,
                    onSendEmailRequested = { called = true }
                )
            }
        }
        composeTestRule.onNodeWithTag(AuthorEmailButtonTestTag).performClick()
        // Assert
        assertTrue(called)
    }
}