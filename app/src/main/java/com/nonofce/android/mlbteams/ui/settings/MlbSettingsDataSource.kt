package com.nonofce.android.mlbteams.ui.settings

import com.nonofce.android.data.source.SettingsDataSource

class MlbSettingsDataSource(private val settings: MLBSettings) : SettingsDataSource {
    override fun getSeasonCount(): Int = settings.getSeasonCountPreferenceValue()

    override fun getDataDuration(): Int = settings.getDataDurationPreferenceValue()

    override fun getLastCacheDate(): Long = settings.getLastCacheDatePreferenceValue()

    override fun updateLastCacheDate() = settings.updateLastCacheDate()
}