package com.nonofce.android.usecases

import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.domain.Player

class LoadPlayer(private val repository: MlbRepository, private val playerId: String) {

    suspend fun invoke(): Player = repository.loadPlayerInfo(playerId)
}