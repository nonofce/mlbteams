package com.nonofce.android.mlbteams.di

import android.app.Application
import androidx.room.Room
import com.nonofce.android.data.source.LocalDataSource
import com.nonofce.android.data.source.RemoteDataSource
import com.nonofce.android.mlbteams.data.database.MLBDatabase
import com.nonofce.android.mlbteams.data.database.RoomDataSource
import com.nonofce.android.mlbteams.data.server.MLBServer
import com.nonofce.android.mlbteams.data.server.MLBService
import com.nonofce.android.mlbteams.data.server.RetrofitDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application): MLBDatabase =
        Room.databaseBuilder(app.applicationContext, MLBDatabase::class.java, "mlbdatabase").build()

    @Provides
    @Singleton
    fun localDataSourceProvider(database: MLBDatabase): LocalDataSource = RoomDataSource(database)

    @Provides
    @Singleton
    fun getRemoteServer(@Named("baseURL") baseUrl: String): MLBServer = MLBServer(baseUrl)

    @Provides
    @Singleton
    fun remoteServiceProvider(server: MLBServer): MLBService =
        server.service

    @Provides
    @Singleton
    fun remoteDataSourceProvider(service: MLBService): RemoteDataSource =
        RetrofitDataSource(service)


}