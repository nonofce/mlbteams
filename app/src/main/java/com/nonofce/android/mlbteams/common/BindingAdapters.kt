package com.nonofce.android.mlbteams.common

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nonofce.android.mlbteams.model.teams.Row
import com.nonofce.android.mlbteams.ui.roster.RosterAdapter
import com.nonofce.android.mlbteams.ui.teams.TeamsAdapter

@BindingAdapter("team")
fun RecyclerView.setTeam(team: List<Row>?) {
    when (adapter) {
        is TeamsAdapter -> (adapter as TeamsAdapter).let {
            it.teams = team ?: emptyList()
        }
    }
}

@BindingAdapter("roster")
fun RecyclerView.setRoster(roster: List<com.nonofce.android.mlbteams.model.roster.Row>?) {
    when (adapter) {
        is RosterAdapter -> (adapter as RosterAdapter).let {
            it.roster = roster ?: emptyList()
        }
    }
}