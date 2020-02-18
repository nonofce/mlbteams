package com.nonofce.android.mlbteams.integration.dagger

import com.nonofce.android.mlbteams.ui.teams.TeamsViewModel
import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.data.source.SettingsDataSource
import com.nonofce.android.mlbteams.ui.player.PlayerViewModel
import com.nonofce.android.mlbteams.ui.settings.MLBSettings
import com.nonofce.android.usecases.LoadPlayer
import com.nonofce.android.usecases.LoadTeams
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
class FakeTeamFragmentModule {

    @Provides
    fun loadTeamsUseCaseProvider(mlbRepository: MlbRepository) = LoadTeams(mlbRepository)

    @Provides
    fun teamsViewModelProvider(loadTeams: LoadTeams, uiDispatcher: CoroutineDispatcher) =
        TeamsViewModel(loadTeams, uiDispatcher)
}

@Subcomponent(modules = [FakeTeamFragmentModule::class])
interface FakeTeamFragmentComponent {
    val teamsViewModel: TeamsViewModel
    val settings: SettingsDataSource
}