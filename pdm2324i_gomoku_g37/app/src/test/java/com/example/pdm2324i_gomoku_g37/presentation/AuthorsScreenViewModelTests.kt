package com.example.pdm2324i_gomoku_g37.presentation

import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.screens.authors.AuthorsScreenViewModel
import com.example.pdm2324i_gomoku_g37.service.FakeGomokuService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
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
        val authors: List<Author> =
            listOf(
                Author(48292, "Tiago Neves", "O melhor programador", "img_tiago", "A48292@alunos.isel.pt"),
            )
        // Arrange
        val serviceMock = mockk<FakeGomokuService> {
            coEvery { fetchAuthors() } coAnswers {
                delay(1000)
                authors
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
        val expected = listOf(
            Author(48292, "Tiago Neves", "O melhor programador", "img_tiago", "A48292@alunos.isel.pt"),
        )
        val serviceMock = mockk<FakeGomokuService> {
            coEvery { fetchAuthors() } coAnswers {
                delay(1000)
                expected
            }
        }
        // Act
        sut.fetchAuthors(serviceMock)
        rule.advanceUntilIdle()

        // Assert
        val actual = sut.authors
        assertNotNull(actual)
        assertEquals(expected, actual)
    }

    @Test
    fun next_author_index_publishes_new_index() {
        // Arrange
        val expected = 0

        // Act
        // Assert
        val actual = sut.index
        assertEquals(expected, actual)
    }

    @Test
    fun nextIndex_publishes_new_index() {
        // Arrange
        val expected = listOf(
            Author(48292, "Tiago Neves", "O melhor programador", "img_tiago", "A48292@alunos.isel.pt"),
            Author(48292, "Tiago Neves", "O melhor programador", "img_tiago", "A48292@alunos.isel.pt"),
            Author(48292, "Tiago Neves", "O melhor programador", "img_tiago", "A48292@alunos.isel.pt"),
            )
        val serviceMock = mockk<FakeGomokuService> {
            coEvery { fetchAuthors() } coAnswers {
                delay(1000)
                expected
            }
        }
        // Act
        sut.fetchAuthors(serviceMock)
        sut.nextIndex()
        rule.advanceUntilIdle()

        // Assert
        val actualAuthors = sut.authors
        val actualIndex = sut.index
        assertNotNull(actualAuthors)
        assertEquals(expected, actualAuthors)
        assertEquals(1,actualIndex)
    }
}