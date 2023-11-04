package com.example.pdm2324i_gomoku_g37

import android.app.Application
import com.example.pdm2324i_gomoku_g37.service.FakeGomokuService
import com.example.pdm2324i_gomoku_g37.service.GomokuService


class GomokuApplication : Application() {
    /*val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .build()

    val gson: Gson = Gson()*/

    //TODO: val service: GomokuService = RealGomokuService()
    val service: GomokuService = FakeGomokuService()
}