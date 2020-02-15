package com.nonofce.android.mlbteams.vm

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nonofce.android.commons.mockedTeam
import com.nonofce.android.data.source.Result
import com.nonofce.android.domain.Team
import com.nonofce.android.mlbteams.ui.teams.TeamsViewModel
import com.nonofce.android.usecases.LoadTeams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TeamsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var loadTeams: LoadTeams

    @Mock
    lateinit var progressVisibility: Observer<Int>

    @Mock
    lateinit var teams: Observer<List<Team>>

    private lateinit var viewModel: TeamsViewModel

    private val season = "2020"

    @Before
    fun setup() {
        viewModel = TeamsViewModel(loadTeams, Dispatchers.Unconfined)
    }

    @Test
    fun `viewModel initialization test`() {

        viewModel.progressVisibility.observeForever(progressVisibility)
        verify(progressVisibility).onChanged(View.GONE)
    }

    private val position = 0

    @Test
    fun `use case is invoked at season change`() {
        runBlocking {
            viewModel.setSelectedSeason(season, position)
            verify(loadTeams).invoke(season)
        }
    }

    @Test
    fun `load teams at season change`() {
        runBlocking {
            val loadedTeams = listOf(mockedTeam)
            whenever(loadTeams.invoke(season)).thenReturn(Result.Success(loadedTeams))
            viewModel.progressVisibility.observeForever(progressVisibility)
            viewModel.teams.observeForever(teams)

            viewModel.setSelectedSeason(season, position)
            verify(progressVisibility).onChanged(View.VISIBLE)
            verify(teams).onChanged(loadedTeams)
            verify(progressVisibility, times(2)).onChanged(View.GONE)
        }
    }
}