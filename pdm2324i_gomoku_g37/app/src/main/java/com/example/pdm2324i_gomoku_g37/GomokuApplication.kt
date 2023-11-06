package com.example.pdm2324i_gomoku_g37

import android.app.Application
import com.example.pdm2324i_gomoku_g37.service.FakeGomokuService
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import com.google.gson.Gson
import okhttp3.OkHttpClient


/**
 * The tag used to identify log messages across the application. Here we elected to use the same
 * tag for all log messages.
 */
const val GomokuAppTestTag = "GomokuAppTestTag"

interface GomokuDependencyProvider {
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
}

/**
 * The application's class used to resolve dependencies, acting as a Service Locator.
 * Dependencies are then injected manually by each Android Component (e.g Activity, Service, etc.).
 */
class GomokuApplication : Application(), GomokuDependencyProvider {
    /**
     * The HTTP client used to perform HTTP requests
     */
    override val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .callTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
            .build()

    /**
     * The JSON serializer/deserializer used to convert JSON into DTOs
     */
    override val gson: Gson = Gson()

    /**
     * The service used for the gomoku app
     */
    override val gomokuService: GomokuService =
        FakeGomokuService()

}