package com.nonofce.android.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.data.source.Result
import com.nonofce.android.domain.PlayerRoster
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoadRosterTest {

    @Mock
    lateinit var mlbRepository: MlbRepository

    private lateinit var loadRoster: LoadRoster

    private val season = "2099"
    private val teamId = "4567"

    @Before
    fun setup() {
        loadRoster = LoadRoster(mlbRepository, season, teamId)
    }

    @Test
    fun `load Roster Use Case Test`() {
        runBlocking {
            val roster = listOf(mockedRoster)
            whenever(mlbRepository.loadRosterByTeam(season, teamId)).thenReturn(
                Result.Success(
                    roster
                )
            )

            val loadedRoster = loadRoster.invoke()

            assertEquals(loadedRoster is Result.Success<List<PlayerRoster>>, true)
            assertEquals(roster, (loadedRoster as Result.Success<List<PlayerRoster>>).value)
        }
    }

    private val mockedRoster = PlayerRoster("L", "John Dow", "P", "2019", "123", "L", "456")
}