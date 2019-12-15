package com.nonofce.android.mlbteams.ui.teams

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nonofce.android.mlbteams.common.Scope
import com.nonofce.android.mlbteams.common.ScopedViewModel
import com.nonofce.android.mlbteams.data.MBLRepository
import com.nonofce.android.mlbteams.model.teams.Row
import kotlinx.coroutines.launch
import java.util.*

class TeamsViewModel(private val mlbRepository: MBLRepository) : ScopedViewModel() {

    private val _progressVisibility = MutableLiveData<Int>()
    val progressVisibility: LiveData<Int>
        get() = _progressVisibility

    private val _seasons = MutableLiveData<List<String>>()
    val seasons: LiveData<List<String>>
        get() = _seasons

    private val _teams = MutableLiveData<List<Row>>()
    val teams: LiveData<List<Row>>
        get() = _teams

    private val _selectedTeam = MutableLiveData<Row>()
    val selectedTeam: LiveData<Row>
        get() = _selectedTeam

    private val _retryVisibility = MutableLiveData<Int>()
    val retryVisibility: LiveData<Int>
        get() = _retryVisibility

    private lateinit var selectedSeason: String

    init {
        //initScope()
        getAvailableSeasons()
        _retryVisibility.value = View.GONE
        _progressVisibility.value = View.GONE
    }

    private fun getAvailableSeasons() {
        val seasons = List(5) { (Calendar.getInstance().get(Calendar.YEAR) - it).toString() }
        _seasons.value = seasons
        selectedSeason = seasons[0]
    }

    fun setSelectedSeason(selectedSeason: String) {
        loadTeams(selectedSeason)
        this.selectedSeason = selectedSeason
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
                    .team_all_season.queryResults.row.shuffled()
            } catch (e: Exception) {
                _retryVisibility.value = View.VISIBLE
            } finally {
                _progressVisibility.value = View.GONE
            }
        }
    }

    fun teamSelected(team: Row) {
        _selectedTeam.value = team
    }

//    override fun onCleared() {
//        destroyScope()
//        super.onCleared()
//    }
}

@Suppress("UNCHECKED_CAST")
class TeamsViewModelFactory(private val mlbRepository: MBLRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        TeamsViewModel(mlbRepository) as T

}