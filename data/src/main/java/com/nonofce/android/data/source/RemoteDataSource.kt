package com.nonofce.android.data.source

import com.nonofce.android.domain.Player
import com.nonofce.android.domain.PlayerRoster
import com.nonofce.android.domain.Team

interface RemoteDataSource {

    suspend fun loadTeamsBySeason(season: String): Result<List<Team>>
    suspend fun loadRosterByTeamAndSeason(season: String, teamId: String): Result<List<PlayerRoster>>
    suspend fun loadPlayerInfo(playerId: String): Player
}