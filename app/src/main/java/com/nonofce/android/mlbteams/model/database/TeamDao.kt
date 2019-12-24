package com.nonofce.android.mlbteams.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TeamDao {

    @Query("SELECT * FROM Team WHERE season = :season")
    fun getTeamsBySeason(season: String): List<Team>

    @Query("SELECT count(team_id) FROM Team WHERE season = :season")
    fun getTeamsCountBySeason(season: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTeams(teams: List<Team>)

    @Query("DELETE FROM Team WHERE season = :season")
    fun deleteBySeason(season: String): Int
}