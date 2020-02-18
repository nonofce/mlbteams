package com.nonofce.android.mlbteams.integration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nonofce.android.domain.Team
import com.nonofce.android.mlbteams.integration.dagger.DaggerMlbComponentTest
import com.nonofce.android.mlbteams.integration.dagger.FakeTeamFragmentComponent
import com.nonofce.android.mlbteams.integration.dagger.FakeTeamFragmentModule
import com.nonofce.android.mlbteams.integration.dagger.fakeTeams
import com.nonofce.android.mlbteams.ui.teams.TeamsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TeamsIntegrationTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var teams: Observer<List<Team>>

    private lateinit var component: FakeTeamFragmentComponent
    private lateinit var viewModel: TeamsViewModel

    @Before
    fun setup() {
        component = DaggerMlbComponentTest
            .factory().create().plus(FakeTeamFragmentModule())
        viewModel = component.teamsViewModel
    }

    @Test
    fun `teams loaded with default season`() {

        viewModel.teams.observeForever(teams)

        viewModel.setSelectedSeason("2020", 0)

        verify(teams).onChanged(fakeTeams)
    }
}