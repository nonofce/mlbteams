package com.nonofce.android.mlbteams.data.database

import com.nonofce.android.data.source.LocalDataSource
import com.nonofce.android.domain.Player
import com.nonofce.android.domain.PlayerRoster
import com.nonofce.android.domain.Team
import com.nonofce.android.mlbteams.data.toLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(private val mlbDatabase: MLBDatabase) : LocalDataSource {

    override suspend fun existsTeamsForSeason(season: String): Boolean =
        withContext(Dispatchers.IO) {
            mlbDatabase.teamDao().getTeamsCountBySeason(season) > 0
        }

    override suspend fun loadTeamsBySeason(season: String): List<Team> =
        withContext(Dispatchers.IO) {
            mlbDatabase.teamDao().getTeamsBySeason(season).map { it.toDomain() }
        }

    override suspend fun saveTeams(season: String, teams: List<Team>) =
        withContext(Dispatchers.IO) {
            mlbDatabase.teamDao().insertTeams(teams.map { it.toLocal(season) })
        }

    override suspend fun deleteTeamsInSeason(season: String): Int =
        withContext(Dispatchers.IO) { mlbDatabase.teamDao().deleteBySeason(season) }

    override suspend fun loadRosterByTeamAndSeason(
        teamId: String,
        season: String
    ): List<PlayerRoster> = withContext(Dispatchers.IO) {
        mlbDatabase.rosterDao().getRosterByTeam(season, teamId).map { it.toDomain() }
    }

    override suspend fun existsRosterForTeamAndSeason(teamId: String, season: String): Boolean =
        withContext(Dispatchers.IO) {
            mlbDatabase.rosterDao().getRosterCount(season, teamId) > 0
        }

    override suspend fun saveRoster(season: String, roster: List<PlayerRoster>) =
        withContext(Dispatchers.IO) {
            mlbDatabase.rosterDao().insertRoster(roster.map { it.toLocal(season) })
        }

    override suspend fun deleteTeamRosterForSeason(teamId: String, season: String): Int =
        withContext(Dispatchers.IO) {
            mlbDatabase.rosterDao().deleteRosterByTeam(season, teamId)
        }

    override suspend fun existsPlayer(playerId: String): Boolean = withContext(Dispatchers.IO) {
        mlbDatabase.playerDetailDao().existsPlayer(playerId) > 0
    }

    override suspend fun loadPlayerInfo(playerId: String): Player = withContext(Dispatchers.IO) {
        mlbDatabase.playerDetailDao().getPlayerDetailById(playerId).toDomain()
    }

    override suspend fun savePlayer(player: Player) = withContext(Dispatchers.IO) {
        mlbDatabase.playerDetailDao().insertPlayerDetail(player.toLocal())
    }

    override suspend fun deletePlayer(playerId: String): Int = withContext(Dispatchers.IO) {
        mlbDatabase.playerDetailDao().deletePlayerDetail(playerId)
    }
}