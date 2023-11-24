package com.example.pdm2324i_gomoku_g37.presentation

import com.example.pdm2324i_gomoku_g37.screens.authors.AuthorsScreenViewModel
import com.example.pdm2324i_gomoku_g37.service.FakeGomokuService
import com.example.pdm2324i_gomoku_g37.service.GomokuAuthors
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthorsScreenViewModelTests {
    /*
    @get:Rule
    val rule = MockMainDispatcherRule(UnconfinedTestDispatcher())

    private val expectedAuthors = GomokuAuthors.authors

    private val service = mockk<GomokuService> {
        coEvery { fetchAuthors() } coAnswers {
            delay(1000)
            expectedAuthors
        }
    }

    private val sut = AuthorsScreenViewModel(service)

    @Test
    fun initially_the_authors_are_null() {
        assertNull(sut.authors)
    }

    @Test
    fun fetchAuthors_triggers_call_to_service() {
        // Arrange
        // Act
        sut.fetchAuthors()

        // Assert
        coVerify(exactly = 1) { service.fetchAuthors() }
    }

    @Test
    fun fetchAuthors_publishes_authors() {
        // Arrange
        // Act
        sut.fetchAuthors()
        rule.advanceUntilIdle()

        // Assert
        val actual = sut.authors
        assertNotNull(actual)
        assertEquals(expectedAuthors, actual)
    }

    @Test
    fun next_author_index_publishes_new_index() {
        // Arrange
        val expectedIndex = 0

        // Act
        // Assert
        val actual = sut.index
        assertEquals(expectedIndex, actual)
    }

    @Test
    fun nextIndex_publishes_new_index() {
        // Arrange
        val expectedIndex = 1

        // Act
        sut.fetchAuthors()
        rule.advanceUntilIdle()
        sut.nextIndex()

        // Assert
        val actualAuthors = sut.authors
        val actualIndex = sut.index
        assertNotNull(actualAuthors)
        assertEquals(expectedAuthors, actualAuthors)
        assertEquals(expectedIndex,actualIndex)
    }

    @Test
    fun prevIndex_publishes_new_index() {
        // Arrange
        val expectedIndex = 2

        // Act
        sut.fetchAuthors()
        rule.advanceUntilIdle()
        sut.prevIndex()

        // Assert
        val actualAuthors = sut.authors
        val actualIndex = sut.index
        assertNotNull(actualAuthors)
        assertEquals(expectedAuthors, actualAuthors)
        assertEquals(expectedIndex,actualIndex)
    }*/

}