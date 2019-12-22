package com.nonofce.android.mlbteams.ui.roster

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.common.basicDiffUtil
import com.nonofce.android.mlbteams.databinding.RosterViewBinding
import com.nonofce.android.mlbteams.model.server.roster.Row

class RosterAdapter(private val listener: (Row) -> Unit) :
    RecyclerView.Adapter<RosterAdapter.RosterViewHolder>() {

    var roster: List<Row> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.team_id == new.team_id })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RosterViewHolder {
        val rosterDataBinding = DataBindingUtil.inflate<RosterViewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.roster_view,
            parent,
            false
        )
        return RosterViewHolder(rosterDataBinding)
    }

    override fun getItemCount(): Int = roster.size

    override fun onBindViewHolder(holder: RosterViewHolder, position: Int) {
        val player = roster[position]
        holder.dataBinding.player = player
        holder.itemView.setOnClickListener { listener(player) }
    }

    class RosterViewHolder(val dataBinding: RosterViewBinding) :
        RecyclerView.ViewHolder(dataBinding.root)
}