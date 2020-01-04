package com.nonofce.android.mlbteams.ui.player

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nonofce.android.mlbteams.common.ScopedViewModel
import com.nonofce.android.mlbteams.data.toRemote
import com.nonofce.android.usecases.LoadPlayer
import kotlinx.coroutines.launch
import com.nonofce.android.mlbteams.data.server.model.player.Row as RemotePlayer

class PlayerViewModel(private val loadPlayer: LoadPlayer) :
    ScopedViewModel() {

    private val _progressVisibility = MutableLiveData<Int>()
    val progressVisibility: LiveData<Int>
        get() = _progressVisibility

    private val _retryVisibility = MutableLiveData<Int>()
    val retryVisibility: LiveData<Int>
        get() = _retryVisibility

    private val _playerInfo = MutableLiveData<RemotePlayer>()
    val playerInfo: LiveData<RemotePlayer>
        get() = _playerInfo

    init {
        _retryVisibility.value = View.GONE
        _progressVisibility.value = View.GONE
        loadPlayerInfo()
    }

    private fun loadPlayerInfo() {
        launch {
            try {
                _retryVisibility.value = View.GONE
                _progressVisibility.value = View.VISIBLE
                _playerInfo.value = loadPlayer.invoke().toRemote()
            } catch (e: Exception) {
                _retryVisibility.value = View.VISIBLE
            } finally {
                _progressVisibility.value = View.GONE
            }
        }
    }

    fun retry() {
        loadPlayerInfo()
    }

}

@Suppress("UNCHECKED_CAST")
class PlayerViewModelFactory(
    private val loadPlayer: LoadPlayer
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        PlayerViewModel(loadPlayer) as T

}