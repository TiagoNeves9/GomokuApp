package com.example.pdm2324i_gomoku_g37

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GomokuApplicationTests {
    @Test
    fun instrumented_tests_use_application_test_context() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.pdm2324i_gomoku_g37", context.packageName)
        assertTrue(
            "Make sure the tests runner is correctly configured in build.gradle\n" +
                    "defaultConfig { testInstrumentationRunner <test runner class name> }",
            context.applicationContext is GomokuTestApplication
        )
    }

    @Test
    fun application_context_contains_dependencies() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        assertTrue(context.applicationContext is GomokuDependenciesContainer)
    }
}