package com.nonofce.android.mlbteams.ui.teams

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.api.load
import com.nonofce.android.domain.Team
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.common.basicDiffUtil
import com.nonofce.android.mlbteams.databinding.TeamViewBinding
import com.nonofce.android.mlbteams.data.server.model.teams.Row
import kotlinx.android.synthetic.main.team_view.view.*

class TeamsAdapter(private val listener: (Team) -> Unit, private val imageLoader: ImageLoader) :
    RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {

    var teams: List<Team> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.team_id == new.team_id })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val teamViewBinding = DataBindingUtil.inflate<TeamViewBinding>(
            LayoutInflater.from(parent.context), R.layout.team_view, parent, false
        )
        return TeamViewHolder(teamViewBinding)
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teams[position]
        holder.dataBinding.team = team
        holder.itemView.teamLogo.load(
            Uri.parse("file:///android_asset/${team.team_id}.svg"),
            imageLoader
        ) {
            crossfade(true)
        }
        holder.itemView.setOnClickListener { listener(team) }
    }

    class TeamViewHolder(val dataBinding: TeamViewBinding) :
        RecyclerView.ViewHolder(dataBinding.root)
}