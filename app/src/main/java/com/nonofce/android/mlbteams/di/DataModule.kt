package com.nonofce.android.mlbteams.di

import com.nonofce.android.data.repository.LocationRepository
import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.data.source.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun repositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        settingsDataSource: SettingsDataSource
    ) = MlbRepository(localDataSource, remoteDataSource, settingsDataSource)

    @Provides
    @Singleton
    fun locationRepositoryProvider(
        permissionChecker: PermissionChecker,
        locationDataSource: LocationDataSource
    ) = LocationRepository(permissionChecker, locationDataSource)
}