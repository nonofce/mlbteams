package com.nonofce.android.mlbteams.dagger

import android.app.Application
import com.nonofce.android.mlbteams.di.*
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Singleton
@Component(modules = [MockUrlModule::class, AppModule::class, DataModule::class, SettingModule::class, DispatcherModule::class])
interface UiMlbComponentTest : MlbComponent {

    fun getMockWebServer(): MockWebServer
    fun getOkHttpClient(): OkHttpClient

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): UiMlbComponentTest
    }
}