package com.nonofce.android.mlbteams.model

class MBLRepository {

    suspend fun loadTeamsBySeason(season: String) =
        MLBDb.service.getTeamsBySeason(season)
            .await()
}