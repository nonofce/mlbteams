package com.nonofce.android.mlbteams.ui.roster

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nonofce.android.mlbteams.common.Event
import com.nonofce.android.mlbteams.common.ScopedViewModel
import com.nonofce.android.mlbteams.data.MLBRepository
import com.nonofce.android.mlbteams.model.server.roster.Row
import kotlinx.coroutines.launch

class RosterViewModel(
    private val mlbRepository: MLBRepository,
    private val season: String,
    private val teamId: String
) : ScopedViewModel() {

    private val _roster = MutableLiveData<List<Row>>()
    val roster: LiveData<List<Row>>
        get() = _roster

    private val _progressVisibility = MutableLiveData<Int>()
    val progressVisibility: LiveData<Int>
        get() = _progressVisibility

    private val _retryVisibility = MutableLiveData<Int>()
    val retryVisibility: LiveData<Int>
        get() = _retryVisibility

    private val _navigateToPlayerInfo = MutableLiveData<Event<String>>()
    val navigateToPlayerInfo: LiveData<Event<String>>
        get() = _navigateToPlayerInfo

    init {
        _retryVisibility.value = View.GONE
        _progressVisibility.value = View.GONE
        loadRosterByTeam()
    }

    private fun loadRosterByTeam() {
        launch {

            try {
                _retryVisibility.value = View.GONE
                _progressVisibility.value = View.VISIBLE
                _roster.value = mlbRepository.loadRosterByTeam(season, season, teamId)
                    .roster_team_alltime.queryResults.row.shuffled()
            } catch (exception: Exception) {
                _retryVisibility.value = View.VISIBLE
            } finally {
                _progressVisibility.value = View.GONE
            }
        }
    }

    fun playerSelected(player: Row) {
        _navigateToPlayerInfo.value = Event(player.player_id)
    }

    fun retry() {
        loadRosterByTeam()
    }
}

@Suppress("UNCHECKED_CAST")
class RosterViewModelFactory(
    private val mlbRepository: MLBRepository,
    private val season: String,
    private val teamId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        RosterViewModel(mlbRepository, season, teamId) as T

}
