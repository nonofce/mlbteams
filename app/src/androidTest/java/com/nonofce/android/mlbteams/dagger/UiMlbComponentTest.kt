package com.nonofce.android.mlbteams.dagger

import android.app.Application
import com.nonofce.android.mlbteams.di.DataModule
import com.nonofce.android.mlbteams.di.DispatcherModule
import com.nonofce.android.mlbteams.di.SettingModule
import com.nonofce.android.mlbteams.ui.player.PlayerFragmentComponent
import com.nonofce.android.mlbteams.ui.player.PlayerFragmentModule
import com.nonofce.android.mlbteams.ui.roster.RosterFragmentComponent
import com.nonofce.android.mlbteams.ui.roster.RosterFragmentModule
import com.nonofce.android.mlbteams.ui.teams.TeamFragmentComponent
import com.nonofce.android.mlbteams.ui.teams.TeamFragmentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MockAppModule::class, DataModule::class, SettingModule::class, DispatcherModule::class])
interface UiMlbComponentTest {

    fun plus(module: TeamFragmentModule): TeamFragmentComponent
    fun plus(module: RosterFragmentModule): RosterFragmentComponent
    fun plus(module: PlayerFragmentModule): PlayerFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): UiMlbComponentTest
    }
}