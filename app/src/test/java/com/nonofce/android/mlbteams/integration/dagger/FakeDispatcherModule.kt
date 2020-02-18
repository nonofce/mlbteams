package com.nonofce.android.mlbteams.integration.dagger

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class FakeDispatcherModule {

    @Provides
    @Singleton
    fun uiDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}