package com.nonofce.android.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.data.source.Result
import com.nonofce.android.domain.Team
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoadTeamsTest {

    @Mock
    lateinit var mlbRepository: MlbRepository

    private lateinit var loadTeams: LoadTeams

    private val season = "2099"

    @Before
    fun setup() {
        loadTeams = LoadTeams(mlbRepository)
    }

    @Test
    fun `load Teams Use Case Test`() {
        runBlocking {
            val teams = listOf(mockedTeam.copy(team_id = "134"))
            whenever(mlbRepository.loadTeamsBySeason(season)).thenReturn(Result.Success(teams))

            val loadedTeams = loadTeams.invoke(season)

            assertEquals(loadedTeams is Result.Success<List<Team>>, true)
            assertEquals(teams, (loadedTeams as Result.Success<List<Team>>).value)
        }
    }

    private val mockedTeam = Team("12", "Team", "TK", "123", "Address", "www.team.com")
}