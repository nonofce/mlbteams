package com.nonofce.android.mlbteams.di

import android.app.Application
import com.nonofce.android.data.source.LocationDataSource
import com.nonofce.android.data.source.PermissionChecker
import com.nonofce.android.mlbteams.common.AndroidPermissionChecker
import com.nonofce.android.mlbteams.data.PlayServiceLocationDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationModule {

    @Provides
    @Singleton
    fun getLocationDataSourceProvider(app: Application): LocationDataSource =
        PlayServiceLocationDataSource(app)

    @Provides
    @Singleton
    fun getPermissionChecker(app: Application): PermissionChecker = AndroidPermissionChecker(app)
}