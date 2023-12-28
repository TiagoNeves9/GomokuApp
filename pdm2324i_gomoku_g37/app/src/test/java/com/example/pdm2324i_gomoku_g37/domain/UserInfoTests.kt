package com.example.pdm2324i_gomoku_g37.domain

import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class UserInfoTests {
    @Test
    fun `creating an user info with an id higher than zero, a non-blank username and a non-blank id succeeds`() {
        UserInfo(id = "1", username = "test user", token = "test token")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `creating an user info with a blank id throws`() {
        UserInfo(id = "", username = "test user", token = "test token")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `creating an user info with a blank username throws`() {
        UserInfo(id = "1", username = "  ", token = "test token")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `creating an user info with a blank token throws`() {
        UserInfo(id = "1", username = "test user", token = "  ")
    }

    @Test
    fun `validating an user info with an id higher than zero, a non-blank username and a non-blank token succeeds`() {
        // Arrange
        // Act
        val result = validateUserInfoParts(id = "1", username = "test user", token = "test token")
        // Assert
        assertTrue(result)
    }

    @Test
    fun `toUserInfoOrNull with an id higher than zero, a non-blank username and a non-blank token succeeds`() {
        // Arrange
        // Act
        val result = toUserInfoOrNull(id = "1", username = "test user", token = "test token")
        // Assert
        assertNotNull(result)
    }

    @Test
    fun `toUserInfoOrNull with an id higher than zero, a blank username and a non-blank token returns null`() {
        // Arrange
        // Act
        val result = toUserInfoOrNull(id = "1", username = "  ", token = "test token")
        // Assert
        assertNull(result)
    }
}