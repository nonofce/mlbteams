package com.nonofce.android.data.source

import com.nonofce.android.domain.Player
import com.nonofce.android.domain.PlayerRoster
import com.nonofce.android.domain.Team

interface LocalDataSource {

    suspend fun existsTeamsForSeason(season: String): Boolean
    suspend fun loadTeamsBySeason(season: String): List<Team>
    suspend fun saveTeams(season: String, teams: List<Team>)
    suspend fun deleteTeamsInSeason(season: String): Int

    suspend fun loadRosterByTeamAndSeason(teamId: String, season: String): List<PlayerRoster>
    suspend fun existsRosterForTeamAndSeason(teamId: String, season: String): Boolean
    suspend fun saveRoster(season: String, roster: List<PlayerRoster>)
    suspend fun deleteTeamRosterForSeason(teamId: String, season: String): Int

    suspend fun existsPlayer(playerId: String): Boolean
    suspend fun loadPlayerInfo(playerId: String): Player
    suspend fun savePlayer(player: Player)
    suspend fun deletePlayer(playerId: String): Int

    suspend fun getLocalTeam(season: String, zipCode: String): Team

}