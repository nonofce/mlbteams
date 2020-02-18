package com.nonofce.android.mlbteams.integration.dagger

import com.nonofce.android.data.source.SettingsDataSource
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
class FakeSettingsModule {

    @Provides
    @Singleton
    fun settingsDataSourceProvider(): SettingsDataSource = FakeMlbSettingsDataSource()
}

class FakeMlbSettingsDataSource : SettingsDataSource {

    private var cacheDate: Long = Date().time
    override fun getSeasonCount(): Int = 5

    override fun getDataDuration(): Int = 0

    override fun getLastCacheDate(): Long = cacheDate

    override fun updateLastCacheDate() {
        cacheDate = Date().time
    }

}
