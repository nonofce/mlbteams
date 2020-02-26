package com.nonofce.android.mlbteams.dagger

import com.nonofce.android.mlbteams.data.server.MLBServer
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Named
import javax.inject.Singleton
import kotlin.concurrent.thread

@Module
class MockUrlModule {

    @Provides
    @Singleton
    fun getMockWebServer(): MockWebServer {
        var mockWebServer: MockWebServer? = null
        val t = thread {
            mockWebServer = MockWebServer()
            mockWebServer?.start()
        }
        t.join()
        return mockWebServer ?: throw NullPointerException()
    }

    @Provides
    @Named("baseURL")
    fun getRemoteBaseURL(server: MockWebServer): String {
        var url = ""
        val t = thread {
            url = server.url("/").toString()
        }
        t.join()
        return url
    }

    @Provides
    @Singleton
    fun getOkHttpClient(server: MLBServer) = server.okHttpClient
}