package com.example.pdm2324i_gomoku_g37.presentation

import com.example.pdm2324i_gomoku_g37.domain.Idle
import com.example.pdm2324i_gomoku_g37.domain.LoadState
import com.example.pdm2324i_gomoku_g37.domain.Loaded
import com.example.pdm2324i_gomoku_g37.domain.Loading
import com.example.pdm2324i_gomoku_g37.domain.UserStatistics
import com.example.pdm2324i_gomoku_g37.screens.rankings.RankingsScreenViewModel
import com.example.pdm2324i_gomoku_g37.service.GomokuRankings
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
class RankingsScreenViewModelTests {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    private val expectedRankings = GomokuRankings.rankings

    private val service = mockk<GomokuService>{
        coEvery { fetchRankings() } coAnswers { expectedRankings }
    }

    @Test
    fun initially_the_rankings_state_flow_is_idle() = runTest {
        //Arrange

        val sut = RankingsScreenViewModel(service)
        val gate = SuspendingGate()

        //Act

        var collectedState: LoadState<List<UserStatistics>?>? = null
        val collectedJob = launch {
            sut.rankings.collect {
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
    fun fetchRankings_triggers_call_to_service() = runTest{

        val sut = RankingsScreenViewModel(service)
        val gate = SuspendingGate()

        val collectJob = launch {
            sut.rankings.collect{
                gate.open()
            }
        }

        sut.fetchRankings()

        withTimeout(TIMEOUT){
            gate.await()
            collectJob.cancelAndJoin()
        }

        coVerify(exactly = 1) { service.fetchRankings() }
    }

    @Test
    fun fetchRankings_emits_to_the_rankings_state_flow_the_loaded_state() = runTest {

        val sut = RankingsScreenViewModel(service)
        val gate = SuspendingGate()
        var lastCollectedState: LoadState<List<UserStatistics>?>? = null

        val collectJob = launch {
            sut.rankings.collectLatest {
                if (it is Loaded){
                    lastCollectedState = it
                    gate.open()
                }
            }
        }

        sut.fetchRankings()

        withTimeout(TIMEOUT){
            gate.await()
            collectJob.cancelAndJoin()
        }

        val loaded = lastCollectedState as? Loaded
        Assert.assertNotNull("Expected Saved but got $lastCollectedState instead", loaded)
        Assert.assertEquals(expectedRankings, loaded?.value?.getOrNull())
    }

    @Test
    fun fetchRankings_emits_to_the_rankings_state_flow_the_loading_state() = runTest{
        val sut = RankingsScreenViewModel(service)
        val gate = SuspendingGate()
        var lastCollectedState : LoadState<List<UserStatistics>?>? = null

        val collectJob = launch {
            sut.rankings.collect{
                if (it is Loading){
                    lastCollectedState = it
                    gate.open()
                }
            }
        }

        sut.fetchRankings()

        withTimeout(TIMEOUT){
            gate.await()
            collectJob.cancelAndJoin()
        }

        val loading = lastCollectedState as? Loading
        Assert.assertNotNull("Expected Loading but got $lastCollectedState instead", loading)
    }

}