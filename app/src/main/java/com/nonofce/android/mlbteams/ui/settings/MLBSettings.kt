package com.nonofce.android.mlbteams.ui.settings

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.os.ConfigurationCompat
import java.text.SimpleDateFormat
import java.util.*

class MLBSettings(context: Context?) {

    private val dateFormatter =
        SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            ConfigurationCompat.getLocales(context?.resources?.configuration!!)[0]
        )

    companion object {
        private const val SEASONS_COUNT_PREFERENCE_NAME = "seasonsCount"
        private const val DATA_DURATION_PREFERENCE_NAME = "dataDuration"
        private const val LAST_CACHE_DATE_PREFERENCE_NAME = "lastCacheDate"
    }


    private val epoch = "1970-01-01 00:00:00"

    private val defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getSeasonCountPreferenceValue(): Int =
        defaultSharedPreferences.getString(SEASONS_COUNT_PREFERENCE_NAME, "5")?.toInt() ?: 5

    fun getDataDurationPreferenceValue(): Int =
        defaultSharedPreferences.getString(DATA_DURATION_PREFERENCE_NAME, "0")?.toInt() ?: 0

    fun getLastCacheDatePreferenceValue(): Long {
        val lastCacheDate =
            defaultSharedPreferences.getString(LAST_CACHE_DATE_PREFERENCE_NAME, epoch)
        return dateFormatter.parse(lastCacheDate).time
    }

    fun updateLastCacheDate() {
        defaultSharedPreferences.edit()
            .putString(LAST_CACHE_DATE_PREFERENCE_NAME, dateFormatter.format(Date())).apply()
    }
}