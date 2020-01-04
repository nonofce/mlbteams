package com.nonofce.android.mlbteams.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nonofce.android.mlbteams.data.database.model.*

@Database(entities = [Team::class, PlayerRoster::class, PlayerDetail::class], version = 1, exportSchema = false)
abstract class MLBDatabase : RoomDatabase() {

    abstract fun teamDao(): TeamDao
    abstract fun rosterDao(): RosterDao
    abstract fun playerDetailDao(): PlayerDetailDao

}