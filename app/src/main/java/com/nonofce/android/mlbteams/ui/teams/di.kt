package com.nonofce.android.mlbteams.ui.teams

import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.data.source.SettingsDataSource
import com.nonofce.android.mlbteams.ui.settings.MLBSettings
import com.nonofce.android.mlbteams.ui.settings.MlbSettingsDataSource
import com.nonofce.android.usecases.LoadTeams
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class TeamFragmentModule {

    @Provides
    fun loadTeamsUseCaseProvider(mlbRepository: MlbRepository) = LoadTeams(mlbRepository)

    @Provides
    fun teamsViewModelProvider(loadTeams: LoadTeams) = TeamsViewModel(loadTeams)
}

@Subcomponent(modules = [TeamFragmentModule::class])
interface TeamFragmentComponent {
    val teamsViewModel: TeamsViewModel
    val settings: MLBSettings
}