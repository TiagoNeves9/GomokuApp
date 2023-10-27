package com.example.pdm2324i_gomoku_g37.presentation

import com.example.pdm2324i_gomoku_g37.screens.authors.AuthorsScreenViewModel
import com.example.pdm2324i_gomoku_g37.service.FakeGomokuService
import com.example.pdm2324i_gomoku_g37.service.GomokuAuthors
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

    @get:Rule
    val rule = MockMainDispatcherRule(UnconfinedTestDispatcher())

    private val sut = AuthorsScreenViewModel()

    @Test
    fun initially_the_authors_are_null() {
        // Arrange
        // Act
        // Assert
        assertNull(sut.authors)
    }

    @Test
    fun fetchAuthors_triggers_call_to_service() {
        // Arrange
        val expectedAuthors = GomokuAuthors.authors
        val serviceMock = mockk<FakeGomokuService> {
            coEvery { fetchAuthors() } coAnswers {
                delay(1000)
                expectedAuthors
            }
        }

        // Act
        sut.fetchAuthors(serviceMock)

        // Assert
        coVerify(exactly = 1) { serviceMock.fetchAuthors() }
    }

    @Test
    fun fetchAuthors_publishes_authors() {
        // Arrange
        val expectedAuthors = GomokuAuthors.authors
        val serviceMock = mockk<FakeGomokuService> {
            coEvery { fetchAuthors() } coAnswers {
                delay(1000)
                expectedAuthors
            }
        }

        // Act
        sut.fetchAuthors(serviceMock)
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
        val expectedAuthors = GomokuAuthors.authors
        val expectedIndex = 1
        val serviceMock = mockk<FakeGomokuService> {
            coEvery { fetchAuthors() } coAnswers {
                delay(1000)
                expectedAuthors
            }
        }

        // Act
        sut.fetchAuthors(serviceMock)
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
        val expectedAuthors = GomokuAuthors.authors
        val expectedIndex = 2
        val serviceMock = mockk<FakeGomokuService> {
            coEvery { fetchAuthors() } coAnswers {
                delay(1000)
                expectedAuthors
            }
        }

        // Act
        sut.fetchAuthors(serviceMock)
        rule.advanceUntilIdle()
        sut.prevIndex()

        // Assert
        val actualAuthors = sut.authors
        val actualIndex = sut.index
        assertNotNull(actualAuthors)
        assertEquals(expectedAuthors, actualAuthors)
        assertEquals(expectedIndex, actualIndex)
    }
}