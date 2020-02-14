package com.nonofce.android.mlbteams.ui.roster

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nonofce.android.data.source.Result
import com.nonofce.android.domain.PlayerRoster
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.common.Event
import com.nonofce.android.mlbteams.common.ScopedViewModel
import com.nonofce.android.usecases.LoadRoster
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class RosterViewModel(
    private val loadRoster: LoadRoster, uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _roster = MutableLiveData<List<PlayerRoster>>()
    val roster: LiveData<List<PlayerRoster>>
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

    private val _retryScreenText = MutableLiveData<Int>()
    val retryScreenText: LiveData<Int>
        get() = _retryScreenText

    init {
        _retryVisibility.value = View.GONE
        _progressVisibility.value = View.GONE
        loadRosterByTeam()
    }

    private fun loadRosterByTeam() {
        launch {
            _retryVisibility.value = View.GONE
            _progressVisibility.value = View.VISIBLE
            when (val result = loadRoster.invoke()) {
                is Result.Success -> _roster.value = result.value
                else -> showError(result)
            }
            _progressVisibility.value = View.GONE
        }
    }

    private fun showError(result: Result<List<PlayerRoster>>) {
        _retryVisibility.value = View.VISIBLE
        _retryScreenText.value =
            if (result is Result.NetworkError) R.string.rosterError else R.string.noDataRoster
    }

    fun playerSelected(player: PlayerRoster) {
        _navigateToPlayerInfo.value = Event(player.player_id)
    }

    fun retry() {
        loadRosterByTeam()
    }
}

