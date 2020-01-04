package com.nonofce.android.mlbteams.ui.teams

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nonofce.android.domain.Team
import com.nonofce.android.mlbteams.common.Event
import com.nonofce.android.mlbteams.common.ScopedViewModel
import com.nonofce.android.mlbteams.data.server.model.teams.Row
import com.nonofce.android.mlbteams.data.toRemote
import com.nonofce.android.usecases.LoadTeams
import kotlinx.coroutines.launch
import java.util.*

class TeamsViewModel(private val loadTeams: LoadTeams) : ScopedViewModel() {

    companion object {
        const val INITIAL_SEASON = -1
    }

    private val _progressVisibility = MutableLiveData<Int>()
    val progressVisibility: LiveData<Int>
        get() = _progressVisibility

    private val _seasons = MutableLiveData<List<String>>()
    val seasons: LiveData<List<String>>
        get() = _seasons

    private val _teams = MutableLiveData<List<Team>>()
    val teams: LiveData<List<Team>>
        get() = _teams

    private val _navigateToRoster = MutableLiveData<Event<Pair<Row, String>>>()
    val navigateToRoster: LiveData<Event<Pair<Row, String>>>
        get() = _navigateToRoster

    private val _retryVisibility = MutableLiveData<Int>()
    val retryVisibility: LiveData<Int>
        get() = _retryVisibility

    private lateinit var selectedSeason: String
    var selectedSeasonPosition = INITIAL_SEASON

    init {
        _retryVisibility.value = View.GONE
        _progressVisibility.value = View.GONE
    }

    fun getAvailableSeasons(seasonsCount: Int) {
        if ((_seasons.value == null) || seasons.value?.size != seasonsCount) {
            val seasons =
                List(seasonsCount) { (Calendar.getInstance().get(Calendar.YEAR) - it).toString() }
            _seasons.value = seasons
            this.selectedSeason = seasons[0]
            this.selectedSeasonPosition = INITIAL_SEASON
        }
    }

    fun setSelectedSeason(selectedSeason: String, position: Int) {
        if (this.selectedSeasonPosition != position) {
            loadTeams(selectedSeason)
            this.selectedSeason = selectedSeason
            this.selectedSeasonPosition = position
        }
    }

    fun retry() {
        loadTeams(this.selectedSeason)
    }

    private fun loadTeams(selectedSeason: String) {
        launch {
            try {
                _teams.value = emptyList()
                _retryVisibility.value = View.GONE
                _progressVisibility.value = View.VISIBLE
                _teams.value = loadTeams.invoke(selectedSeason)
            } catch (e: Exception) {
                _retryVisibility.value = View.VISIBLE
            } finally {
                _progressVisibility.value = View.GONE
            }
        }
    }

    fun teamSelected(team: Team) {
        val args = (team.toRemote() to selectedSeason)
        _navigateToRoster.value = Event(args)
    }
}

@Suppress("UNCHECKED_CAST")
class TeamsViewModelFactory(
    private val loadTeams: LoadTeams
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        TeamsViewModel(loadTeams) as T

}