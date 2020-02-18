package com.nonofce.android.mlbteams.integration

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nonofce.android.mlbteams.data.toRemote
import com.nonofce.android.mlbteams.integration.dagger.DaggerMlbComponentTest
import com.nonofce.android.mlbteams.integration.dagger.fakePlayer
import com.nonofce.android.mlbteams.ui.player.PlayerFragmentComponent
import com.nonofce.android.mlbteams.ui.player.PlayerFragmentModule
import com.nonofce.android.mlbteams.ui.player.PlayerViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.nonofce.android.mlbteams.data.server.model.player.Row as RemotePlayer

@RunWith(MockitoJUnitRunner::class)
class PlayerIntegrationTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var player: Observer<RemotePlayer>

    @Mock
    lateinit var app: Application

    private lateinit var component: PlayerFragmentComponent
    private lateinit var viewModel: PlayerViewModel

    @Before
    fun setup() {
        component = DaggerMlbComponentTest
            .factory().create().plus(PlayerFragmentModule("456"))
        viewModel = component.playerViewModel
    }

    @Test
    fun `player details loaded for a given player`() {

        viewModel.playerInfo.observeForever(player)

        verify(player).onChanged(fakePlayer.toRemote())
    }
}