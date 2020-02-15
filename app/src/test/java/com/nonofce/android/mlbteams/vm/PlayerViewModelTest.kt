package com.nonofce.android.mlbteams.vm

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nonofce.android.commons.mockedPlayer
import com.nonofce.android.mlbteams.data.toRemote
import com.nonofce.android.mlbteams.ui.player.PlayerViewModel
import com.nonofce.android.usecases.LoadPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.nonofce.android.mlbteams.data.server.model.player.Row as Player

@RunWith(MockitoJUnitRunner::class)
class PlayerViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var loadPlayer: LoadPlayer

    @Mock
    lateinit var progressVisibility: Observer<Int>

    @Mock
    lateinit var playerInfo: Observer<Player>

    private lateinit var viewModel: PlayerViewModel

    @Before
    fun setup() {
        viewModel = PlayerViewModel(loadPlayer, Dispatchers.Unconfined)
    }

    @Test
    fun `load player information for a given player`() {
        runBlocking {
            whenever(loadPlayer.invoke()).thenReturn(mockedPlayer)
            viewModel.progressVisibility.observeForever(progressVisibility)
            viewModel.playerInfo.observeForever(playerInfo)

            viewModel.loadPlayerInfo()

            verify(progressVisibility).onChanged(View.VISIBLE)
            verify(playerInfo).onChanged(mockedPlayer.toRemote())
            verify(progressVisibility, times(2)).onChanged(View.GONE)
        }
    }
}