package com.nonofce.android.usecases

import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.domain.PlayerRoster

class LoadRoster(
    private val repository: MlbRepository,
    private val season: String,
    private val teamId: String
) {

    suspend fun invoke(): List<PlayerRoster> =
        repository.loadRosterByTeam(season, teamId).shuffled()
}