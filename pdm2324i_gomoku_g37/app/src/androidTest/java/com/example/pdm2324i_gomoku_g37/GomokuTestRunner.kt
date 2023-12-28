package com.example.pdm2324i_gomoku_g37

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.example.pdm2324i_gomoku_g37.domain.UserInfo
import com.example.pdm2324i_gomoku_g37.domain.UserInfoRepository
import com.example.pdm2324i_gomoku_g37.service.FakeGomokuService
import com.example.pdm2324i_gomoku_g37.service.GomokuService
import io.mockk.coEvery
import io.mockk.mockk

class GomokuTestApplication: GomokuDependenciesContainer, Application() {

    override val gomokuService: GomokuService = FakeGomokuService()

    override var userInfoRepository: UserInfoRepository = mockk {
        coEvery { getUserInfo() } returns UserInfo("test_id", "test_username", "test_token")
    }

}

@Suppress("unused")
class GomokuTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, GomokuTestApplication::class.java.name, context)
    }
}