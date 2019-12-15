package com.nonofce.android.mlbteams.ui.teams

import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.model.teams.Row

@BindingAdapter("teamAddress")
fun TextView.setTeamAddress(address: String) {
    text = address
        .replace("&#xa;", ", ")
        .replace("&#x9;", ", ")
}

@BindingAdapter("seasons")
fun Spinner.setSeason(seasons: List<String>) {
    adapter = ArrayAdapter(
        context!!,
        R.layout.support_simple_spinner_dropdown_item,
        seasons
    )
}
