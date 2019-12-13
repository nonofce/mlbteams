package com.nonofce.android.mlbteams.data

class MBLRepository {

    suspend fun loadTeamsBySeason(season: String) =
        MLBDb.service.getTeamsBySeason(season)
            .await()
}