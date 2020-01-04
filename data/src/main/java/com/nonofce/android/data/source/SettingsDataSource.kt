package com.nonofce.android.data.source

interface SettingsDataSource {

    fun getSeasonCount(): Int
    fun getDataDuration(): Int
    fun getLastCacheDate(): Long
    fun updateLastCacheDate()
}