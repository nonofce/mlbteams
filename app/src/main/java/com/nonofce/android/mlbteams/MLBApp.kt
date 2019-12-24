package com.nonofce.android.mlbteams

import android.app.Application
import androidx.room.Room
import com.nonofce.android.mlbteams.data.MLBDatabase

class MLBApp : Application() {

    lateinit var database: MLBDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, MLBDatabase::class.java, "mlbdatabase").build()

    }
}