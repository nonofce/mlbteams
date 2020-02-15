package com.nonofce.android.mlbteams.di

import android.app.Application
import androidx.room.Room
import com.nonofce.android.data.source.LocalDataSource
import com.nonofce.android.data.source.RemoteDataSource
import com.nonofce.android.data.source.SettingsDataSource
import com.nonofce.android.mlbteams.data.database.MLBDatabase
import com.nonofce.android.mlbteams.data.database.RoomDataSource
import com.nonofce.android.mlbteams.data.server.MLBServer
import com.nonofce.android.mlbteams.data.server.MLBService
import com.nonofce.android.mlbteams.data.server.RetrofitDataSource
import com.nonofce.android.mlbteams.ui.settings.MLBSettings
import com.nonofce.android.mlbteams.ui.settings.MlbSettingsDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
    fun remoteServiceProvider(): MLBService = MLBServer.service

    @Provides
    @Singleton
    fun remoteDataSourceProvider(service: MLBService): RemoteDataSource =
        RetrofitDataSource(service)


}