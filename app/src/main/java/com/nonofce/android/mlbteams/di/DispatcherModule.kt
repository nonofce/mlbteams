package com.nonofce.android.mlbteams.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class DispatcherModule {

    @Provides
    @Singleton
    fun uiDispatcher(): CoroutineDispatcher = Dispatchers.Main
}