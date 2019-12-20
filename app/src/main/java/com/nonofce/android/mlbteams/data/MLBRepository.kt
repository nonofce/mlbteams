package com.nonofce.android.mlbteams.data

class MLBRepository {

    suspend fun loadTeamsBySeason(season: String) =
        MLBDb.service.getTeamsBySeason(season)
            .await()

    suspend fun loadRosterByTeam(startSeason: String, endSeason: String, teamId: String) =
        MLBDb.service.getRosterByTeam(
            startSeason, endSeason, teamId
        ).await()
}