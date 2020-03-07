package com.nonofce.android.mlbteams.integration.dagger

import com.nonofce.android.mlbteams.di.DataModule
import com.nonofce.android.mlbteams.di.LocationModule
import com.nonofce.android.mlbteams.ui.player.PlayerFragmentComponent
import com.nonofce.android.mlbteams.ui.player.PlayerFragmentModule
import com.nonofce.android.mlbteams.ui.roster.RosterFragmentComponent
import com.nonofce.android.mlbteams.ui.roster.RosterFragmentModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FakeAppModule::class, DataModule::class, FakeSettingsModule::class, FakeDispatcherModule::class])
interface MlbComponentTest {

    fun plus(module: FakeTeamFragmentModule): FakeTeamFragmentComponent
    fun plus(module: RosterFragmentModule): RosterFragmentComponent
    fun plus(module: PlayerFragmentModule): PlayerFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(): MlbComponentTest
    }
}