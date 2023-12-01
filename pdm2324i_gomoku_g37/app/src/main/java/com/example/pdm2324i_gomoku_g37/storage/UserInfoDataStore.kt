package com.example.pdm2324i_gomoku_g37.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.UserInfoRepository
import kotlinx.coroutines.flow.first

private const val USER_ID_KEY = "Id"
private const val USER_USERNAME_KEY = "Username"
private const val USER_TOKEN_KEY = "Token"

/**
 * A user information repository implementation supported in DataStore, the
 * modern alternative to SharedPreferences.
 */

class UserInfoDataStore(private val store: DataStore<Preferences>) : UserInfoRepository {

    private val idKey = stringPreferencesKey(USER_ID_KEY)
    private val usernameKey = stringPreferencesKey(USER_USERNAME_KEY)
    private val tokenKey = stringPreferencesKey(USER_TOKEN_KEY)

    override suspend fun getUserInfo(): UserInfo? {
        val preferences = store.data.first()
        val id = preferences[idKey]
        val username = preferences[usernameKey]
        val token = preferences[tokenKey]
        return if (id != null && username != null && token != null) UserInfo(id, username, token)
                else null
    }

    override suspend fun updateUserInfo(userInfo: UserInfo) {
        store.edit { preferences ->
            preferences[idKey] = userInfo.id
            preferences[usernameKey] = userInfo.username
            preferences[tokenKey] = userInfo.token
        }
    }
}