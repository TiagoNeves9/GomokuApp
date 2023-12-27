package com.example.pdm2324i_gomoku_g37.presentation

import com.example.pdm2324i_gomoku_g37.domain.Idle
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.Loaded
import com.example.pdm2324i_gomoku_g37.domain.Loading
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.WaitingLobby
import com.example.pdm2324i_gomoku_g37.screens.play.PlayScreenViewModel
import com.example.pdm2324i_gomoku_g37.service.GomokuLobbies
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import com.example.pdm2324i_gomoku_g37.utils.SuspendingGate
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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

private const val TIMEOUT = 2000L
@OptIn(ExperimentalCoroutinesApi::class)
class PlayScreenViewModelTests {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    private val expectedLobbies =  GomokuLobbies.lobbies

    private val service = mockk<GomokuService>{
        coEvery { fetchLobbies() } coAnswers { expectedLobbies }
    }

    private val userinfo = mockk<UserInfo>()

    private val sut = PlayScreenViewModel(service,userinfo)
    private val gate = SuspendingGate()
    private var lastCollectedState: LoadState<List<WaitingLobby>?>? = null
    @Test
    fun initially_the_lobbies_state_flow_is_idle() = runTest{

        var collectedState: LoadState<List<WaitingLobby>?>? = null
        val collectedJob = launch {
            sut.lobbiesFlow.collect{
                collectedState = it
                gate.open()
            }
        }

        withTimeout(TIMEOUT){
            gate.await()
            collectedJob.cancelAndJoin()
        }

        Assert.assertTrue("Expected Idle but got $collectedState instead", collectedState is Idle)
    }


    @Test
    fun fetchLobbies_triggers_call_to_service() = runTest{

        val collectJob = launch {
            sut.lobbiesFlow.collect{
                gate.open()
            }
        }

        sut.fetchLobbies()

        withTimeout(TIMEOUT){
            gate.await()
            collectJob.cancelAndJoin()
        }

        coVerify(exactly = 1) { service.fetchLobbies()}
    }

    @Test
    fun fetchLobbies_emits_to_the_lobbies_state_flow_the_loaded_state() = runTest {

        val collectJob = launch {
            sut.lobbiesFlow.collectLatest {
                if (it is Loaded){
                    lastCollectedState = it
                    gate.open()
                }
            }
        }

        sut.fetchLobbies()

        withTimeout(TIMEOUT){
            gate.await()
            collectJob.cancelAndJoin()
        }

        val loaded = lastCollectedState as? Loaded
        Assert.assertNotNull("Expected Saved but got $lastCollectedState instead", loaded)
        Assert.assertEquals(expectedLobbies,loaded?.value?.getOrNull())
    }

    @Test
    fun fetchLobbies_emits_to_the_lobbies_state_flow_the_loading_state() = runTest {
        val collectJob = launch {
            sut.lobbiesFlow.collect{
                if (it is Loading){
                    lastCollectedState = it
                    gate.open()
                }
            }
        }

        sut.fetchLobbies()

        withTimeout(TIMEOUT){
            gate.await()
            collectJob.cancelAndJoin()
        }

        val loading = lastCollectedState as? Loading
        Assert.assertNotNull("Expected Loading but got $lastCollectedState instead", loading)
    }
}