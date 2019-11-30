package com.nonofce.android.mlbteams.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nonofce.android.mlbteams.common.Scope
import com.nonofce.android.mlbteams.model.MBLRepository
import com.nonofce.android.mlbteams.model.results.teams.Row
import kotlinx.coroutines.launch
import java.util.*

class TeamsViewModel(private val mlbRepository: MBLRepository) : ViewModel(),
    Scope by Scope.Impl() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    private var currentSelectedSeason = Calendar.getInstance().get(Calendar.YEAR).toString()

    sealed class UiModel {
        class SeasonSelection(val seasons: List<String>) : UiModel()
        class TeamsLoaded(val teams: List<Row>) : UiModel()
        class Error(val e: Exception) : UiModel()
        class TeamSelected(val team: Row) : UiModel()
        object StartLoading : UiModel()
        object EndLoading : UiModel()
    }

    init {
        initScope()
        getAvailableSeasons()
    }

    private fun getAvailableSeasons() {
        _model.value =
            UiModel.SeasonSelection(List(5) { (Calendar.getInstance().get(Calendar.YEAR) - it).toString() })
    }

    fun setSelectedSeason(selectedSeason: String) {
        currentSelectedSeason = selectedSeason
        loadTeams()
    }

    private fun loadTeams() {
        launch {
            try {
                _model.value = UiModel.StartLoading
                _model.value = UiModel.TeamsLoaded(emptyList())
                _model.value =
                    UiModel.TeamsLoaded(mlbRepository.loadTeamsBySeason(currentSelectedSeason).team_all_season.queryResults.row.shuffled())
            } catch (e: Exception) {
                _model.value = UiModel.Error(e)
            } finally {
                _model.value = UiModel.EndLoading
            }
        }
    }

    fun teamSelected(team: Row) {
        _model.value = UiModel.TeamSelected(team)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}

@Suppress("UNCHECKED_CAST")
class TeamsViewModelFactory(private val mlbRepository: MBLRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        TeamsViewModel(mlbRepository) as T

}