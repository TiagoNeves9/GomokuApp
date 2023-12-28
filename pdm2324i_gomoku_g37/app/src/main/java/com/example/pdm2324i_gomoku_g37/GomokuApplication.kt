package com.example.pdm2324i_gomoku_g37

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.pdm2324i_gomoku_g37.domain.UserInfoRepository
import com.example.pdm2324i_gomoku_g37.service.FakeGomokuService
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import com.example.pdm2324i_gomoku_g37.service.RealGomokuService
import com.example.pdm2324i_gomoku_g37.storage.UserInfoDataStore
import com.google.gson.Gson
import okhttp3.OkHttpClient


/**
 * The tag used to identify log messages across the application. Here we elected to use the same
 * tag for all log messages.
 */
const val GomokuAppTestTag = "GomokuAppTestTag"

interface GomokuDependenciesContainer {
    /**
     * The HTTP client used to perform HTTP requests
     */
    val httpClient: OkHttpClient

    /**
     * The JSON serializer/deserializer used to convert JSON into DTOs
     */
    val gson: Gson

    /**
     * The service used to fetch jokes
     */
    val gomokuService: GomokuService

    /**
     * UserInfo repository
     */
    val userInfoRepository: UserInfoRepository
}

/**
 * The application's class used to resolve dependencies, acting as a Service Locator.
 * Dependencies are then injected manually by each Android Component (e.g Activity, Service, etc.).
 */
class GomokuApplication : Application(), GomokuDependenciesContainer {

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_info")

    /**
     * The HTTP client used to perform HTTP requests
     */
    override val httpClient: OkHttpClient =
        OkHttpClient
            .Builder()
            .callTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
            .build()

    /**
     * The JSON serializer/deserializer used to convert JSON into DTOs
     */
    override val gson: Gson = Gson()

    /**
     * The service used for the gomoku app
     */
    //override val gomokuService: GomokuService = FakeGomokuService()
    override val gomokuService: GomokuService = RealGomokuService(httpClient, gson)

    /**
     * The UserInfo repository
     */
    override val userInfoRepository: UserInfoRepository
        get() = UserInfoDataStore(dataStore)

}