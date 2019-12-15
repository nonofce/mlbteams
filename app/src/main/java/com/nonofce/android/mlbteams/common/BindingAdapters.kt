package com.nonofce.android.mlbteams.common

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nonofce.android.mlbteams.model.teams.Row
import com.nonofce.android.mlbteams.ui.teams.TeamsAdapter

@BindingAdapter("items")
fun RecyclerView.setItems(teams: List<Row>?) {
    when (adapter) {
        is TeamsAdapter -> (adapter as TeamsAdapter).let {
            it.teams = teams ?: emptyList()
        }
    }
}