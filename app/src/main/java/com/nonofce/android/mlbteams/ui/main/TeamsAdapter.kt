package com.nonofce.android.mlbteams.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.common.basicDiffUtil
import com.nonofce.android.mlbteams.common.inflate
import com.nonofce.android.mlbteams.model.results.teams.Row
import kotlinx.android.synthetic.main.team_view.view.*

class TeamsAdapter(private val listener: (Row) -> Unit) :
    RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {

    var teams: List<Row> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.team_id == new.team_id })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = parent.inflate(R.layout.team_view, false)
        return TeamViewHolder(view)
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teams[position]
        holder.bind(team)
        holder.itemView.setOnClickListener {
            listener(team)
        }

    }

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(team: Row) {
            itemView.teamName.text = team.name_display_full
            itemView.teamAddress.text = team.address
                .replace("&#xa;", ", ")
                .replace("&#x9;", ", ")
            itemView.teamWebUrl.text = team.website_url
        }

    }
}