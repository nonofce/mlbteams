package com.nonofce.android.mlbteams.data

import android.util.Log
import com.nonofce.android.mlbteams.MLBApp
import com.nonofce.android.mlbteams.model.database.convertToUi
import com.nonofce.android.mlbteams.model.server.player.convertToDb
import com.nonofce.android.mlbteams.model.server.roster.convertToDb
import com.nonofce.android.mlbteams.model.server.teams.Row
import com.nonofce.android.mlbteams.model.server.teams.convertToDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MLBRepository(private val application: MLBApp) {

    companion object {
        val TAG = "MLBApp::MLBRepository"
    }

    suspend fun loadTeamsBySeason(season: String): List<Row> = withContext(Dispatchers.IO) {

        val teamsBySeason = application.database.teamDao().getTeamsCountBySeason(season)
        if (shouldBeCached(teamsBySeason)) {
            Log.d(TAG, "Caching Teams in DB")
            val results = MLBServer.service.getTeamsBySeason(season)
                .await().team_all_season.queryResults.row.map {
                it.convertToDb(season)
            }
            val deletedTeams = application.database.teamDao().deleteBySeason(season)
            Log.d(TAG, "Deleted $deletedTeams teams from season $season")
            application.database.teamDao().insertTeams(results)
        }
        application.database.teamDao().getTeamsBySeason(season).map {
            it.convertToUi()
        }
    }

    private fun shouldBeCached(itemCount: Int): Boolean = itemCount <= 0


    suspend fun loadRosterByTeam(season: String, teamId: String) =
        withContext(Dispatchers.IO) {

            val rosterByTeamAndSeason =
                application.database.rosterDao().getRosterCount(season, teamId)
            if (shouldBeCached(rosterByTeamAndSeason)) {
                Log.d(TAG, "Caching Roster in DB")
                val results = MLBServer.service.getRosterByTeam(
                    season, season, teamId
                ).await().roster_team_alltime.queryResults.row.map {
                    it.convertToDb(season)
                }

                val deletedPlayers =
                    application.database.rosterDao().deleteRosterByTeam(season, teamId)
                Log.d(TAG, "Deleted $deletedPlayers players from team $teamId from season $season")
                application.database.rosterDao().insertRoster(results)
            }
            application.database.rosterDao().getRosterByTeam(season, teamId).map {
                it.convertToUi()
            }
        }


    suspend fun loadPlayerInfo(playerId: String) = withContext(Dispatchers.IO) {

        val playerDetailCount = application.database.playerDetailDao().existsPlayer(playerId)
        if (playerDetailCount == 0) {
            Log.d(TAG, "Caching PlayerDetail in DB")
            val result = MLBServer.service.getPlayerInfo(playerId).await()
                .player_info.queryResults.row.convertToDb()
            val deletedPlayerDetail =
                application.database.playerDetailDao().deletePlayerDetail(playerId)
            Log.d(TAG, "Deleted $deletedPlayerDetail players")
            application.database.playerDetailDao().insertPlayerDetail(result)
        }
        application.database.playerDetailDao().getPlayerDetailById(playerId).convertToUi()
    }
}