package com.nonofce.android.mlbteams.di

import android.app.Application
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
@Component(
    modules = [UrlModule::class, AppModule::class, DataModule::class, SettingModule::class,
        DispatcherModule::class, LocationModule::class]
)
interface MlbComponent {

    fun plus(module: TeamFragmentModule): TeamFragmentComponent
    fun plus(module: RosterFragmentModule): RosterFragmentComponent
    fun plus(module: PlayerFragmentModule): PlayerFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MlbComponent
    }
}