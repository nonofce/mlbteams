package com.nonofce.android.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.nonofce.android.commons.mockedPlayer
import com.nonofce.android.data.repository.MlbRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoadPlayerTest {

    @Mock
    lateinit var mlbRepository: MlbRepository

    lateinit var loadPlayer: LoadPlayer

    private val playerId = "234"

    @Before
    fun setup() {
        loadPlayer = LoadPlayer(mlbRepository, playerId)
    }

    @Test
    fun `load Player Use Case Test`() {
        runBlocking {
            whenever(mlbRepository.loadPlayerInfo(playerId)).thenReturn(mockedPlayer)

            val loadedPlayer = loadPlayer.invoke()

            assertEquals(loadedPlayer, mockedPlayer)
        }
    }
}