package com.nonofce.android.mlbteams.ui.main

import android.content.Context
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.api.load
import coil.decode.SvgDecoder
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.common.basicDiffUtil
import com.nonofce.android.mlbteams.common.inflate
import com.nonofce.android.mlbteams.model.results.teams.Row
import kotlinx.android.synthetic.main.team_view.view.*

class TeamsAdapter(private val listener: (Row) -> Unit, private val context: Context) :
    RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {

    var teams: List<Row> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.team_id == new.team_id })

    val imageLoader = ImageLoader(context) {
        componentRegistry {
            add(SvgDecoder(context))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = parent.inflate(R.layout.team_view, false)
        return TeamViewHolder(view)
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teams[position]
        holder.bind(team, imageLoader)
        holder.itemView.setOnClickListener {
            listener(team)
        }

    }

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(team: Row, imageLoader: ImageLoader) {
            with(itemView) {
                teamName.text = team.name_display_full
                teamAddress.text = team.address
                    .replace("&#xa;", ", ")
                    .replace("&#x9;", ", ")
                teamWebUrl.text = team.website_url
            }
            itemView.teamLogo.load(
                Uri.parse("file:///android_asset/${team.team_id}.svg"),
                imageLoader
            ) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }

    }
}