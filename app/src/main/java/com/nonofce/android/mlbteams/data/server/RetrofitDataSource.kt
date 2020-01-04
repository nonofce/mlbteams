package com.nonofce.android.mlbteams.data.server

import com.nonofce.android.data.source.RemoteDataSource
import com.nonofce.android.domain.Player
import com.nonofce.android.domain.PlayerRoster
import com.nonofce.android.domain.Team

class RetrofitDataSource(private val service: MLBService) : RemoteDataSource {

    override suspend fun loadTeamsBySeason(season: String): List<Team> =
        service.getTeamsBySeason(season).await()
            .team_all_season.queryResults.row?.let {
            it.map { it.toDomain() }
        } ?: emptyList()

    override suspend fun loadRosterByTeamAndSeason(
        season: String,
        teamId: String
    ): List<PlayerRoster> = service.getRosterByTeam(
        season,
        season,
        teamId
    ).await().roster_team_alltime.queryResults.row?.let {
        it.map { it.toDomain() }
    } ?: emptyList()

    override suspend fun loadPlayerInfo(playerId: String): Player =
        service.getPlayerInfo(playerId).await().player_info.queryResults.row.toDomain()
}