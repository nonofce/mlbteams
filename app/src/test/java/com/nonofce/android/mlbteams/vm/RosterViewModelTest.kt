package com.nonofce.android.mlbteams.vm

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nonofce.android.commons.mockedRoster
import com.nonofce.android.data.source.Result
import com.nonofce.android.domain.PlayerRoster
import com.nonofce.android.mlbteams.ui.roster.RosterViewModel
import com.nonofce.android.usecases.LoadRoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RosterViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var loadRoster: LoadRoster

    @Mock
    lateinit var progressVisibility: Observer<Int>

    @Mock
    lateinit var roster: Observer<List<PlayerRoster>>

    private lateinit var viewModel: RosterViewModel

    @Before
    fun setup() {
        viewModel = RosterViewModel(loadRoster, Dispatchers.Unconfined)
    }

    @Test
    fun `load roster for given season and team`() {
        runBlocking {
            val loadedRoster = listOf(mockedRoster)
            whenever(loadRoster.invoke()).thenReturn(Result.Success(loadedRoster))
            viewModel.progressVisibility.observeForever(progressVisibility)
            viewModel.roster.observeForever(roster)

            viewModel.loadRosterByTeam()

            verify(progressVisibility).onChanged(View.VISIBLE)
            verify(roster).onChanged(loadedRoster)
            verify(progressVisibility, times(2)).onChanged(View.GONE)
        }
    }

}