package com.nonofce.android.mlbteams.data

import android.util.Log
import com.nonofce.android.mlbteams.MLBApp
import com.nonofce.android.mlbteams.model.database.convertToUi
import com.nonofce.android.mlbteams.model.server.player.convertToDb
import com.nonofce.android.mlbteams.model.server.roster.convertToDb
import com.nonofce.android.mlbteams.model.server.teams.Row
import com.nonofce.android.mlbteams.model.server.teams.convertToDb
import com.nonofce.android.mlbteams.ui.settings.MLBSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class MLBRepository(
    private val application: MLBApp,
    private val mlbsettings: MLBSettings
) {

    companion object {
        private const val TAG = "MLBApp::MLBRepository"
        private const val MILLISECONDS_IN_A_MINUTE = 60 * 1000
    }

    suspend fun loadTeamsBySeason(season: String): List<Row> = withContext(Dispatchers.IO) {

        val teamsBySeason = application.database.teamDao().getTeamsCountBySeason(season)
        if (dataShouldBeCached(teamsBySeason)) {
            Log.d(TAG, "Caching Teams in DB")
            val results = MLBServer.service.getTeamsBySeason(season)
                .await().team_all_season.queryResults.row.map {
                it.convertToDb(season)
            }
            val deletedTeams = application.database.teamDao().deleteBySeason(season)
            Log.d(TAG, "Deleted $deletedTeams teams from season $season")
            application.database.teamDao().insertTeams(results)
            mlbsettings.updateLastCacheDate()
        }

        application.database.teamDao().getTeamsBySeason(season).map {
            it.convertToUi()
        }

    }

    private fun dataShouldBeCached(itemCount: Int): Boolean {
        val dataDuration = mlbsettings.getDataDurationPreferenceValue()
        val lastCacheTime = mlbsettings.getLastCacheDatePreferenceValue()
        Log.d(TAG, (Date().time - lastCacheTime).toString())
        return itemCount <= 0 || dataDuration == 0 || (Date().time - lastCacheTime > dataDuration * MILLISECONDS_IN_A_MINUTE)
    }


    suspend fun loadRosterByTeam(season: String, teamId: String) =
        withContext(Dispatchers.IO) {

            val rosterByTeamAndSeason =
                application.database.rosterDao().getRosterCount(season, teamId)
            if (dataShouldBeCached(rosterByTeamAndSeason)) {
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
                mlbsettings.updateLastCacheDate()
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
            mlbsettings.updateLastCacheDate()
        }
        application.database.playerDetailDao().getPlayerDetailById(playerId).convertToUi()
    }
}