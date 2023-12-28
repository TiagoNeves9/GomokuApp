package isel.pdm.pdm2324i_gomoku_g37

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import isel.pdm.pdm2324i_gomoku_g37.domain.Author
import isel.pdm.pdm2324i_gomoku_g37.domain.LoadState
import isel.pdm.pdm2324i_gomoku_g37.domain.loaded
import com.pdm.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorCardTestTag
import com.pdm.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorEmailButtonTestTag
import com.pdm.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorNextTestTag
import com.pdm.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorPrevTestTag
import com.pdm.pdm2324i_gomoku_g37.helpers.AuthorsScreenTestTags.AuthorNoAuthorTestTag
import com.pdm.pdm2324i_gomoku_g37.screens.authors.AuthorsHandlers
import com.pdm.pdm2324i_gomoku_g37.screens.authors.AuthorsScreen
import com.pdm.pdm2324i_gomoku_g37.service.GomokuAuthors
import com.pdm.pdm2324i_gomoku_g37.ui.theme.GomokuTheme
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test


class AuthorsScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val authors: isel.pdm.pdm2324i_gomoku_g37.domain.LoadState<List<isel.pdm.pdm2324i_gomoku_g37.domain.Author>?> =
        isel.pdm.pdm2324i_gomoku_g37.domain.loaded(Result.success(GomokuAuthors.authors))

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