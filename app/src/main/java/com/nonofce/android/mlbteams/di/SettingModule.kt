package com.nonofce.android.mlbteams.di

import android.app.Application
import com.nonofce.android.data.source.SettingsDataSource
import com.nonofce.android.mlbteams.ui.settings.MLBSettings
import com.nonofce.android.mlbteams.ui.settings.MlbSettingsDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SettingModule {

    @Provides
    @Singleton
    fun settingsServiceProvider(app: Application): MLBSettings = MLBSettings(app.applicationContext)

    @Provides
    @Singleton
    fun settingsDataSourceProvider(settings: MLBSettings): SettingsDataSource =
        MlbSettingsDataSource(settings)
}