package com.nonofce.android.mlbteams.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nonofce.android.mlbteams.data.database.model.Team

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

    @Query("SELECT * FROM Team WHERE season = :season AND address_zip = :zipCode")
    fun getTeamBySeasonAndZipCode(season: String, zipCode: String): Team?
}