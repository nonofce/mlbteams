package com.nonofce.android.mlbteams.integration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nonofce.android.domain.PlayerRoster
import com.nonofce.android.mlbteams.integration.dagger.DaggerMlbComponentTest
import com.nonofce.android.mlbteams.integration.dagger.fakeRoster
import com.nonofce.android.mlbteams.ui.roster.RosterFragmentComponent
import com.nonofce.android.mlbteams.ui.roster.RosterFragmentModule
import com.nonofce.android.mlbteams.ui.roster.RosterViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RosterIntegrationTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var roster: Observer<List<PlayerRoster>>

    private lateinit var component: RosterFragmentComponent
    private lateinit var viewModel: RosterViewModel

    @Before
    fun setup() {
        component = DaggerMlbComponentTest
            .factory().create().plus(RosterFragmentModule("2020", "123"))
        viewModel = component.rosterViewModel
    }

    @Test
    fun `teams loaded with default season`() {

        viewModel.roster.observeForever(roster)

        verify(roster).onChanged(fakeRoster)
    }
}