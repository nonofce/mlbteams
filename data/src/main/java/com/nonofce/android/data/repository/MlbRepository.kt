package com.nonofce.android.data.repository

import com.nonofce.android.data.source.LocalDataSource
import com.nonofce.android.data.source.RemoteDataSource
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
        private const val TAG = "MLBApp::MLBRepository"
        private const val MILLISECONDS_IN_A_MINUTE = 60 * 1000
    }

    suspend fun loadTeamsBySeason(season: String): List<Team> {
        val existsTeams = localDataSource.existsTeamsForSeason(season)
        if (dataShouldBeCached(existsTeams)) {
            val teams = remoteDataSource.loadTeamsBySeason(season)
            localDataSource.deleteTeamsInSeason(season)
            localDataSource.saveTeams(season, teams)
            settingsDataSource.updateLastCacheDate()
        }
        return localDataSource.loadTeamsBySeason(season)
    }

    suspend fun loadRosterByTeam(season: String, teamId: String): List<PlayerRoster> {
        val existsRosterForTeamInSeason =
            localDataSource.existsRosterForTeamAndSeason(season = season, teamId = teamId)
        if (dataShouldBeCached(existsRosterForTeamInSeason)) {
            val roster = remoteDataSource.loadRosterByTeamAndSeason(season, teamId)
            localDataSource.deleteTeamRosterForSeason(teamId, season)
            localDataSource.saveRoster(season, roster)
            settingsDataSource.updateLastCacheDate()
        }
        return localDataSource.loadRosterByTeamAndSeason(teamId, season)
    }

    suspend fun loadPlayerInfo(playerId: String): Player{
        val existsPlayer = localDataSource.existsPlayer(playerId)
        if(dataShouldBeCached(existsPlayer)){
            val player = remoteDataSource.loadPlayerInfo(playerId)
            localDataSource.deletePlayer(playerId)
            localDataSource.savePlayer(player)
            settingsDataSource.updateLastCacheDate()
        }
        return localDataSource.loadPlayerInfo(playerId)
    }

    private fun dataShouldBeCached(existsItem: Boolean): Boolean {
        val dataDuration = settingsDataSource.getDataDuration()
        val lastCacheTime = settingsDataSource.getLastCacheDate()
        return !existsItem || dataDuration == 0 || (Date().time - lastCacheTime > dataDuration * MILLISECONDS_IN_A_MINUTE)
    }
}