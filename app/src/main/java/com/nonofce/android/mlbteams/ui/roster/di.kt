package com.nonofce.android.mlbteams.ui.roster

import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.usecases.LoadRoster
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class RosterFragmentModule(private val seasonId: String, private val teamId: String) {

    @Provides
    fun loadRosterUseCaseProvider(mlbRepository: MlbRepository) =
        LoadRoster(mlbRepository, seasonId, teamId)

    @Provides
    fun rosterViewModelProvider(loadRoster: LoadRoster) = RosterViewModel(loadRoster)

}

@Subcomponent(modules = [RosterFragmentModule::class])
interface RosterFragmentComponent {
    val rosterViewModel: RosterViewModel
}