package com.nonofce.android.mlbteams.di

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class UrlModule {

    @Provides
    @Named("baseURL")
    fun getRemoteBaseURL() = "https://lookup-service-prod.mlb.com/json/"
}