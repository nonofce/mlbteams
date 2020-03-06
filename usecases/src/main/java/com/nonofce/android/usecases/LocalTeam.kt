package com.nonofce.android.usecases

import com.nonofce.android.data.repository.LocationRepository
import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.data.source.Result
import com.nonofce.android.domain.Team

class LocalTeam(
    private val mlbRepository: MlbRepository,
    private val locationRepository: LocationRepository
) {

    suspend fun invoke(season: String): Result<Team> {
        val zipCode = locationRepository.getZipCode()
        val localTeam = mlbRepository.getLocalTeam(season, zipCode)
        return localTeam
    }
}