package com.nonofce.android.mlbteams.ui.player

import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.usecases.LoadPlayer
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
class PlayerFragmentModule(private val playerId: String) {

    @Provides
    fun loadPlayerUseCaseProvider(mlbRepository: MlbRepository) =
        LoadPlayer(mlbRepository, playerId)

    @Provides
    fun playerViewModelProvider(loadPlayer: LoadPlayer, uiDispatcher: CoroutineDispatcher) =
        PlayerViewModel(loadPlayer, uiDispatcher)
}

@Subcomponent(modules = [PlayerFragmentModule::class])
interface PlayerFragmentComponent {
    val playerViewModel: PlayerViewModel
}