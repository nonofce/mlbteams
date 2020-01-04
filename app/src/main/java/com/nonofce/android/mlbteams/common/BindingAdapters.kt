package com.nonofce.android.mlbteams.common

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nonofce.android.domain.PlayerRoster
import com.nonofce.android.domain.Team
import com.nonofce.android.mlbteams.ui.roster.RosterAdapter
import com.nonofce.android.mlbteams.ui.teams.TeamsAdapter

@BindingAdapter("team")
fun RecyclerView.setTeam(team: List<Team>?) {
    when (adapter) {
        is TeamsAdapter -> (adapter as TeamsAdapter).let {
            it.teams = emptyList()
            it.teams = team ?: emptyList()
        }
    }
}

@BindingAdapter("roster")
fun RecyclerView.setRoster(roster: List<PlayerRoster>?) {
    when (adapter) {
        is RosterAdapter -> (adapter as RosterAdapter).let {
            it.roster = roster ?: emptyList()
        }
    }
}