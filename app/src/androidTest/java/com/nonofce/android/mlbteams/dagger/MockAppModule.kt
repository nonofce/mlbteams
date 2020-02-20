package com.nonofce.android.mlbteams.dagger

import android.app.Application
import androidx.room.Room
import com.nonofce.android.data.source.LocalDataSource
import com.nonofce.android.data.source.RemoteDataSource
import com.nonofce.android.mlbteams.data.database.MLBDatabase
import com.nonofce.android.mlbteams.data.database.RoomDataSource
import com.nonofce.android.mlbteams.data.server.MLBServer
import com.nonofce.android.mlbteams.data.server.MLBService
import com.nonofce.android.mlbteams.data.server.RetrofitDataSource
import com.nonofce.android.mlbteams.di.AppModule
import com.nonofce.android.mlbteams.utils.MockWebServerRule
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Named
import javax.inject.Singleton
import kotlin.concurrent.thread

@Module
class MockAppModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application): MLBDatabase =
        Room.databaseBuilder(app.applicationContext, MLBDatabase::class.java, "mlbdatabase").build()

    @Provides
    @Singleton
    fun localDataSourceProvider(database: MLBDatabase): LocalDataSource = RoomDataSource(database)

    @Provides
    @Singleton
    fun getMockWebServer() = MockWebServer()

    @Provides
    @Named("baseURL")
    fun getRemoteBaseURL(server: MockWebServer) : String{
        var url = ""
        val t = thread {
            url = server.url("/").toString()
        }
        t.join()
        return url
    }

    @Provides
    @Singleton
    fun remoteServiceProvider(@Named("baseURL") baseUrl: String): MLBService =
        MLBServer(baseUrl).service

    @Provides
    @Singleton
    fun remoteDataSourceProvider(service: MLBService): RemoteDataSource =
        RetrofitDataSource(service)

    @Provides
    @Singleton
    fun getMockWebServerRule(server: MockWebServer) = MockWebServerRule(server)
}