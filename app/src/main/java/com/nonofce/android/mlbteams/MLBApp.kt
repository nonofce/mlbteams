package com.nonofce.android.mlbteams

import android.app.Application
import androidx.room.Room
import com.nonofce.android.mlbteams.data.database.MLBDatabase
import com.nonofce.android.mlbteams.di.DaggerMlbComponent
import com.nonofce.android.mlbteams.di.MlbComponent

class MLBApp : Application() {

    lateinit var mlbComponent: MlbComponent

    override fun onCreate() {
        super.onCreate()

        mlbComponent = DaggerMlbComponent.factory().create(this)

    }
}