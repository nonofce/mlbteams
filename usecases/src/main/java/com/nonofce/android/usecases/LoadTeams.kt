package com.nonofce.android.usecases

import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.data.source.Result
import com.nonofce.android.domain.Team

class LoadTeams(private val repository: MlbRepository) {

    suspend fun invoke(season: String): Result<List<Team>> =
        when (val result = repository.loadTeamsBySeason(season)) {
            is Result.Success -> Result.Success(result.value.shuffled())
            else -> result
        }

}