package com.nonofce.android.mlbteams.ui.teams

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nonofce.android.mlbteams.common.Event
import com.nonofce.android.mlbteams.common.ScopedViewModel
import com.nonofce.android.mlbteams.data.MLBRepository
import com.nonofce.android.mlbteams.model.server.teams.Row
import kotlinx.coroutines.launch
import java.util.*

class TeamsViewModel(private val mlbRepository: MLBRepository) : ScopedViewModel() {

    companion object {
        const val INITIAL_SEASON = -1
    }

    private val _progressVisibility = MutableLiveData<Int>()
    val progressVisibility: LiveData<Int>
        get() = _progressVisibility

    private val _seasons = MutableLiveData<List<String>>()
    val seasons: LiveData<List<String>>
        get() = _seasons

    private val _teams = MutableLiveData<List<Row>>()
    val teams: LiveData<List<Row>>
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
        getAvailableSeasons()
        _retryVisibility.value = View.GONE
        _progressVisibility.value = View.GONE
    }

    private fun getAvailableSeasons() {
        val seasons = List(5) { (Calendar.getInstance().get(Calendar.YEAR) - it).toString() }
        _seasons.value = seasons
        selectedSeason = seasons[0]
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
                _retryVisibility.value = View.GONE
                _progressVisibility.value = View.VISIBLE
                _teams.value = emptyList()
                _teams.value = mlbRepository.loadTeamsBySeason(selectedSeason)
                    .shuffled()
            } catch (e: Exception) {
                _retryVisibility.value = View.VISIBLE
            } finally {
                _progressVisibility.value = View.GONE
            }
        }
    }

    fun teamSelected(team: Row) {
        val args = (team to selectedSeason)
        _navigateToRoster.value = Event(args)
    }
}

@Suppress("UNCHECKED_CAST")
class TeamsViewModelFactory(private val mlbRepository: MLBRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        TeamsViewModel(mlbRepository) as T

}