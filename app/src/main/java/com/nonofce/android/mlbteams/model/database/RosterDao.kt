package com.nonofce.android.mlbteams.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RosterDao {

    @Query("SELECT * FROM PlayerRoster WHERE team_id = :teamId and season = :season")
    fun getRosterByTeam(season: String, teamId: String): List<PlayerRoster>

    @Query("SELECT count(player_id) FROM PlayerRoster where team_id = :teamId and season = :season")
    fun getRosterCount(season: String, teamId: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRoster(roster: List<PlayerRoster>)

    @Query("DELETE FROM PlayerRoster WHERE team_id = :teamId and season = :season")
    fun deleteRosterByTeam(season: String, teamId: String): Int
}