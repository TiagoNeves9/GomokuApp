package com.example.pdm2324i_gomoku_g37.presentation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
class AuthorsScreenViewModelTests {
    @get:Rule
    val rule = MockMainDispatcherRule(UnconfinedTestDispatcher())
}