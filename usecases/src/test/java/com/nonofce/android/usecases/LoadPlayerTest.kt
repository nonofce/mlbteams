package com.nonofce.android.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.domain.Player
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

    private val mockedPlayer = Player(
        "23",
        "City",
        "Country",
        "M",
        "100",
        "6.2",
        "1",
        "John Doe",
        "P",
        "2010",
        "",
        "230",
        "456"
    )
}