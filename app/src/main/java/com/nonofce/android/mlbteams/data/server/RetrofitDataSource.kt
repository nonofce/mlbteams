package com.nonofce.android.mlbteams.data.server

import com.nonofce.android.data.source.RemoteDataSource
import com.nonofce.android.data.source.Result
import com.nonofce.android.domain.Player
import com.nonofce.android.domain.PlayerRoster
import com.nonofce.android.domain.Team
import com.nonofce.android.mlbteams.common.serverCall
import kotlinx.coroutines.Dispatchers

class RetrofitDataSource(private val service: MLBService) : RemoteDataSource {

    override suspend fun loadTeamsBySeason(season: String): Result<List<Team>> =
        when (val result = serverCall(Dispatchers.IO) {
            service.getTeamsBySeason(season).await()
                .team_all_season.queryResults.row?.let { teams ->
                teams.map { team ->
                    team.toDomain()
                }
            } ?: emptyList<Team>()
        }) {
            is Result.Success -> if (result.value.isEmpty()) Result.Empty else result
            else -> result
        }

    override suspend fun loadRosterByTeamAndSeason(
        season: String,
        teamId: String
    ): Result<List<PlayerRoster>> = when (val result = serverCall(Dispatchers.IO) {
        service.getRosterByTeam(
            season,
            season,
            teamId
        ).await().roster_team_alltime.queryResults.row?.let { roster ->
            roster.map { playerRoster ->
                playerRoster.toDomain()
            }
        } ?: emptyList()
    }) {
        is Result.Success -> if (result.value.isEmpty()) Result.Empty else result
        else -> result
    }

    override suspend fun loadPlayerInfo(playerId: String): Player =
        service.getPlayerInfo(playerId).await().player_info.queryResults.row.toDomain()
}