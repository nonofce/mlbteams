package com.nonofce.android.mlbteams.integration.dagger

import com.nonofce.android.data.repository.LocationRepository
import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.data.source.SettingsDataSource
import com.nonofce.android.mlbteams.ui.teams.TeamsViewModel
import com.nonofce.android.usecases.LoadTeams
import com.nonofce.android.usecases.LocalTeam
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
class FakeTeamFragmentModule {

    @Provides
    fun loadTeamsUseCaseProvider(mlbRepository: MlbRepository) = LoadTeams(mlbRepository)

    @Provides
    fun localTeamUseCaseProvider(
        mlbRepository: MlbRepository,
        locationRepository: LocationRepository
    ) = LocalTeam(mlbRepository, locationRepository)

    @Provides
    fun teamsViewModelProvider(
        loadTeams: LoadTeams,
        localTeam: LocalTeam,
        uiDispatcher: CoroutineDispatcher
    ) =
        TeamsViewModel(loadTeams, localTeam, uiDispatcher)
}

@Subcomponent(modules = [FakeTeamFragmentModule::class])
interface FakeTeamFragmentComponent {
    val teamsViewModel: TeamsViewModel
    val settings: SettingsDataSource
}