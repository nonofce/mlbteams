package com.nonofce.android.usecases

import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.domain.Team

class LoadTeams(private val repository: MlbRepository) {

    suspend fun invoke(season: String): List<Team> = repository.loadTeamsBySeason(season).shuffled()
}