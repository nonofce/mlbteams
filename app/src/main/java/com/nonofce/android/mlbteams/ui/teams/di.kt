package com.nonofce.android.mlbteams.ui.teams

import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.mlbteams.ui.settings.MLBSettings
import com.nonofce.android.usecases.LoadTeams
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
class TeamFragmentModule {

    @Provides
    fun loadTeamsUseCaseProvider(mlbRepository: MlbRepository) = LoadTeams(mlbRepository)

    @Provides
    fun teamsViewModelProvider(loadTeams: LoadTeams, uiDispatcher: CoroutineDispatcher) =
        TeamsViewModel(loadTeams, uiDispatcher)
}

@Subcomponent(modules = [TeamFragmentModule::class])
interface TeamFragmentComponent {
    val teamsViewModel: TeamsViewModel
    val settings: MLBSettings
}