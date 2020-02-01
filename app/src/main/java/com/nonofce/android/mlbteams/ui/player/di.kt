package com.nonofce.android.mlbteams.ui.player

import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.usecases.LoadPlayer
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class PlayerFragmentModule(private val playerId: String) {

    @Provides
    fun loadPlayerUseCaseProvider(mlbRepository: MlbRepository) =
        LoadPlayer(mlbRepository, playerId)

    @Provides
    fun playerViewModelProvider(loadPlayer: LoadPlayer) = PlayerViewModel(loadPlayer)
}

@Subcomponent(modules = [PlayerFragmentModule::class])
interface PlayerFragmentComponent {
    val playerViewModel: PlayerViewModel
}