package com.example.pdm2324i_gomoku_g37.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@OptIn(ExperimentalCoroutinesApi::class)
class UserInfoDataStoreTests {

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = TestScope(UnconfinedTestDispatcher()),
            produceFile = { tmpFolder.newFile("test.preferences_pb") }
        )

    @Test
    fun getUserInfo_returns_null_if_no_user_info_is_stored(): Unit = runTest {
        // Arrange
        val sut = UserInfoDataStore(testDataStore)
        // Act
        val userInfo = sut.getUserInfo()
        // Assert
        assertNull(userInfo)
    }

    @Test
    fun getUserInfo_returns_the_previously_stored_value(): Unit = runTest {
        // Arrange
        val sut = UserInfoDataStore(testDataStore)
        val expected = UserInfo(1, "test user", "test token")
        sut.updateUserInfo(expected)
        // Act
        val result = sut.getUserInfo()
        // Assert
        assertEquals(expected, result)
    }
}