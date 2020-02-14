package com.nonofce.android.data.repository

import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nonofce.android.commons.mockedPlayer
import com.nonofce.android.commons.mockedRoster
import com.nonofce.android.commons.mockedTeam
import com.nonofce.android.data.source.LocalDataSource
import com.nonofce.android.data.source.RemoteDataSource
import com.nonofce.android.data.source.Result
import com.nonofce.android.data.source.SettingsDataSource
import com.nonofce.android.domain.PlayerRoster
import com.nonofce.android.domain.Team
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MlbRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var settingsDataSource: SettingsDataSource

    lateinit var mlbRepository: MlbRepository

    private val season = "2099"
    private val teamId = "1234"
    private val playerId = "456"

    @Before
    fun setup() {
        mlbRepository = MlbRepository(localDataSource, remoteDataSource, settingsDataSource)
    }

    @Test
    fun `load Teams By Season from Remote`() {

        runBlocking {
            val remoteTeams = listOf(mockedTeam)
            whenever(localDataSource.existsTeamsForSeason(season)).thenReturn(false)
            whenever(settingsDataSource.getDataDuration()).thenReturn(0)
            whenever(remoteDataSource.loadTeamsBySeason(season)).thenReturn(
                Result.Success(remoteTeams)
            )
            whenever(localDataSource.loadTeamsBySeason(season)).thenReturn(remoteTeams)

            val teams = mlbRepository.loadTeamsBySeason(season)
            assertEquals(teams is Result.Success<List<Team>>, true)
            assertEquals(remoteTeams, (teams as Result.Success).value)

            verify(localDataSource).saveTeams(season, remoteTeams)
        }

    }

    @Test
    fun `load Teams By Season from Local`() {

        runBlocking {
            val localTeams = listOf(mockedTeam)
            whenever(localDataSource.existsTeamsForSeason(season)).thenReturn(true)
            whenever(settingsDataSource.getDataDuration()).thenReturn(1)
            whenever(settingsDataSource.getLastCacheDate()).thenReturn(Date().time + 1000)

            whenever(localDataSource.loadTeamsBySeason(season)).thenReturn(localTeams)

            val teams = mlbRepository.loadTeamsBySeason(season)
            assertEquals(teams is Result.Success<List<Team>>, true)
            assertEquals(localTeams, (teams as Result.Success).value)

            verify(remoteDataSource, never()).loadTeamsBySeason(season)
        }

    }

    @Test
    fun `load Roster By Season and Team from Remote`() {

        runBlocking {
            val remoteRoster = listOf(mockedRoster)
            whenever(localDataSource.existsRosterForTeamAndSeason(teamId, season)).thenReturn(false)
            whenever(settingsDataSource.getDataDuration()).thenReturn(0)
            whenever(remoteDataSource.loadRosterByTeamAndSeason(season, teamId)).thenReturn(
                Result.Success(remoteRoster)
            )
            whenever(localDataSource.loadRosterByTeamAndSeason(teamId, season)).thenReturn(
                remoteRoster
            )

            val roster = mlbRepository.loadRosterByTeam(season, teamId)
            assertEquals(roster is Result.Success<List<PlayerRoster>>, true)
            assertEquals(remoteRoster, (roster as Result.Success).value)

            verify(localDataSource).saveRoster(season, remoteRoster)
        }

    }

    @Test
    fun `load Roster By Season and Team from Local`() {

        runBlocking {
            val localRoster = listOf(mockedRoster)
            whenever(localDataSource.existsRosterForTeamAndSeason(teamId, season)).thenReturn(true)
            whenever(settingsDataSource.getDataDuration()).thenReturn(1)
            whenever(settingsDataSource.getLastCacheDate()).thenReturn(Date().time + 1000)

            whenever(localDataSource.loadRosterByTeamAndSeason(teamId, season)).thenReturn(
                localRoster
            )

            val roster = mlbRepository.loadRosterByTeam(season, teamId)
            assertEquals(roster is Result.Success<List<PlayerRoster>>, true)
            assertEquals(localRoster, (roster as Result.Success).value)

            verify(remoteDataSource, never()).loadRosterByTeamAndSeason(season, teamId)
        }

    }

    @Test
    fun `load Player Info from Remote`() {
        runBlocking {
            whenever(localDataSource.existsPlayer(playerId)).thenReturn(false)
            whenever(settingsDataSource.getDataDuration()).thenReturn(0)
            whenever(remoteDataSource.loadPlayerInfo(playerId)).thenReturn(mockedPlayer)
            whenever(localDataSource.loadPlayerInfo(playerId)).thenReturn(mockedPlayer)

            val player = mlbRepository.loadPlayerInfo(playerId)
            assertEquals(mockedPlayer, player)

            verify(localDataSource).savePlayer(mockedPlayer)
        }

    }

    @Test
    fun `load Player Info from Local`() {
        runBlocking {
            whenever(localDataSource.existsPlayer(playerId)).thenReturn(true)
            whenever(settingsDataSource.getDataDuration()).thenReturn(1)
            whenever(settingsDataSource.getLastCacheDate()).thenReturn(Date().time + 1000)
            whenever(localDataSource.loadPlayerInfo(playerId)).thenReturn(mockedPlayer)

            val player = mlbRepository.loadPlayerInfo(playerId)
            assertEquals(mockedPlayer, player)

            verify(localDataSource, never()).savePlayer(mockedPlayer)
        }

    }

}