package com.nonofce.android.data.repository

import com.nonofce.android.data.source.LocalDataSource
import com.nonofce.android.data.source.RemoteDataSource
import com.nonofce.android.data.source.Result
import com.nonofce.android.data.source.SettingsDataSource
import com.nonofce.android.domain.Player
import com.nonofce.android.domain.PlayerRoster
import com.nonofce.android.domain.Team
import java.util.*

class MlbRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val settingsDataSource: SettingsDataSource
) {

    companion object {
        private const val MILLISECONDS_IN_A_MINUTE = 60 * 1000
    }

    suspend fun loadTeamsBySeason(season: String): Result<List<Team>> {
        val existsTeams = localDataSource.existsTeamsForSeason(season)
        if (dataShouldBeCached(existsTeams)) {
            val result = remoteDataSource.loadTeamsBySeason(season)
            if (result is Result.NetworkError || result is Result.Empty) {
                return result
            }
            localDataSource.deleteTeamsInSeason(season)
            localDataSource.saveTeams(season, (result as Result.Success).value)
            settingsDataSource.updateLastCacheDate()
        }
        return Result.Success(localDataSource.loadTeamsBySeason(season))
    }

    suspend fun loadRosterByTeam(season: String, teamId: String): Result<List<PlayerRoster>> {
        val existsRosterForTeamInSeason =
            localDataSource.existsRosterForTeamAndSeason(season = season, teamId = teamId)
        if (dataShouldBeCached(existsRosterForTeamInSeason)) {
            val result = remoteDataSource.loadRosterByTeamAndSeason(season, teamId)
            if (result is Result.NetworkError || result == Result.Empty) {
                return result
            }
            localDataSource.deleteTeamRosterForSeason(teamId, season)
            localDataSource.saveRoster(season, (result as Result.Success).value)
            settingsDataSource.updateLastCacheDate()
        }
        return Result.Success(localDataSource.loadRosterByTeamAndSeason(teamId, season))
    }

    suspend fun loadPlayerInfo(playerId: String): Player {
        val existsPlayer = localDataSource.existsPlayer(playerId)
        if (dataShouldBeCached(existsPlayer)) {
            val player = remoteDataSource.loadPlayerInfo(playerId)
            localDataSource.deletePlayer(playerId)
            localDataSource.savePlayer(player)
            settingsDataSource.updateLastCacheDate()
        }
        val loadPlayerInfo = localDataSource.loadPlayerInfo(playerId)
        return loadPlayerInfo
    }

    suspend fun getLocalTeam(season: String, zipCode: String): Result<Team> =
        Result.Success(localDataSource.getLocalTeam(season, zipCode))

    private fun dataShouldBeCached(existsItem: Boolean): Boolean {
        val dataDuration = settingsDataSource.getDataDuration()
        val lastCacheTime = settingsDataSource.getLastCacheDate()
        return !existsItem || dataDuration == 0 || (Date().time - lastCacheTime > dataDuration * MILLISECONDS_IN_A_MINUTE)
    }
}