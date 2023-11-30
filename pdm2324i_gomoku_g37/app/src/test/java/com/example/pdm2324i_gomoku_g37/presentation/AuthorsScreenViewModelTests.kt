package com.example.pdm2324i_gomoku_g37.presentation

import com.example.pdm2324i_gomoku_g37.domain.Author
import com.example.pdm2324i_gomoku_g37.domain.Idle
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.Loaded
import com.example.pdm2324i_gomoku_g37.domain.Loading
import com.example.pdm2324i_gomoku_g37.screens.authors.AuthorsScreenViewModel
import com.example.pdm2324i_gomoku_g37.service.GomokuAuthors
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import com.example.pdm2324i_gomoku_g37.utils.SuspendingGate
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

private const val WAIT_TIMEOUT = 2000L

@OptIn(ExperimentalCoroutinesApi::class)
class AuthorsScreenViewModelTests {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    private val expectedAuthors = GomokuAuthors.authors

    private val service = mockk<GomokuService> {
        coEvery { fetchAuthors() } coAnswers { expectedAuthors }
    }

    @Test
    fun initially_the_authors_state_flow_is_idle() = runTest {
        // Arrange
        val sut = AuthorsScreenViewModel(service)
        val gate = SuspendingGate()
        // Act
        var collectedState: LoadState<List<Author>?>? = null
        val collectJob = launch {
            sut.authorsList.collect {
                collectedState = it
                gate.open()
            }
        }

        // Lets wait for the flow to emit the first value
        withTimeout(WAIT_TIMEOUT) {
            gate.await()
            collectJob.cancelAndJoin()
        }

        // Assert
        Assert.assertTrue("Expected Idle bot got $collectedState instead", collectedState is Idle)
    }

    @Test
    fun fetchAuthors_triggers_call_to_service() = runTest {
        // Arrange
        val sut = AuthorsScreenViewModel(service)
        val gate = SuspendingGate()
        // Act
        val collectJob = launch {
            sut.authorsList.collect {
                gate.open()
            }
        }

        sut.fetchAuthors()

        // Lets wait for the flow to emit the first value
        withTimeout(WAIT_TIMEOUT) {
            gate.await()
            collectJob.cancelAndJoin()
        }

        // Assert
        coVerify(exactly = 1) { service.fetchAuthors() }

    }

    @Test
    fun fetchAuthors_emits_to_the_authors_state_flow_the_loaded_state() = runTest {
        // Arrange
        val sut = AuthorsScreenViewModel(service)
        val gate = SuspendingGate()
        var lastCollectedState: LoadState<List<Author>?>? = null
        // Act
        val collectJob = launch {
            sut.authorsList.collectLatest  {
                if (it is Loaded) {
                    lastCollectedState = it
                    gate.open()
                }
            }
        }

        sut.fetchAuthors()

        // Lets wait for the flow to emit the latest value
        withTimeout(WAIT_TIMEOUT) {
            gate.await()
            collectJob.cancelAndJoin()
        }

        // Assert
        val loaded = lastCollectedState as? Loaded
        Assert.assertNotNull("Expected Saved but got $lastCollectedState instead", loaded)
        Assert.assertEquals(expectedAuthors, loaded?.value?.getOrNull())
    }

    @Test
    fun fetchAuthors_emits_to_the_authors_state_flow_the_loading_state() = runTest {
        // Arrange
        val sut = AuthorsScreenViewModel(service)
        val gate = SuspendingGate()
        var lastCollectedState: LoadState<List<Author>?>? = null
        // Act
        val collectJob = launch {
            sut.authorsList.collect {
                if (it is Loading) {
                    lastCollectedState = it
                    gate.open()
                }
            }
        }

        sut.fetchAuthors()

        // Lets wait for the flow to emit the latest value
        withTimeout(WAIT_TIMEOUT) {
            gate.await()
            collectJob.cancelAndJoin()
        }

        // Assert
        val loading = lastCollectedState as? Loading
        assertNotNull("Expected Loading but got $lastCollectedState instead", loading)
    }

    @Test
    fun initially_the_index_is_zero() {
        // Arrange
        val expectedIndex = 0
        val sut = AuthorsScreenViewModel(service)

        // Act
        // Assert
        val actual = sut.index
        assertEquals(expectedIndex, actual)
    }

    @Test
    fun next_author_increases_index() = runTest {
        // Arrange
        val expectedIndex = 1
        val sut = AuthorsScreenViewModel(service)
        val gate = SuspendingGate()
        // Act
        val collectJob = launch {
            sut.authorsList.collect {
                gate.open()
            }
        }

        sut.fetchAuthors()
        rule.advanceUntilIdle()
        sut.nextIndex()

        // Lets wait for the flow to emit the first value
        withTimeout(WAIT_TIMEOUT) {
            gate.await()
            collectJob.cancelAndJoin()
        }

        // Assert
        assertEquals(expectedIndex, sut.index)
    }

    @Test
    fun prev_author_is_last_author_index() = runTest {
        // Arrange
        val expectedIndex = expectedAuthors.lastIndex
        val sut = AuthorsScreenViewModel(service)
        val gate = SuspendingGate()
        // Act
        val collectJob = launch {
            sut.authorsList.collect {
                gate.open()
            }
        }

        sut.fetchAuthors()
        rule.advanceUntilIdle()
        sut.prevIndex()

        // Lets wait for the flow to emit the first value
        withTimeout(WAIT_TIMEOUT) {
            gate.await()
            collectJob.cancelAndJoin()
        }

        // Assert
        assertEquals(expectedIndex, sut.index)
    }
}