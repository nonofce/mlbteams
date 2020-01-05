package com.nonofce.android.usecases

import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.data.source.Result
import com.nonofce.android.domain.PlayerRoster

class LoadRoster(
    private val repository: MlbRepository,
    private val season: String,
    private val teamId: String
) {

    suspend fun invoke(): Result<List<PlayerRoster>> =
        when (val result = repository.loadRosterByTeam(season, teamId)) {
            is Result.Success -> Result.Success(result.value.shuffled())
            else -> result
        }
}