package com.nonofce.android.mlbteams.data.database.model

import androidx.room.Entity
import com.nonofce.android.mlbteams.data.server.model.teams.Row as ServerTeam

@Entity(primaryKeys = ["season", "team_id"])
data class Team(
    val season: String,
    val address_zip: String,
    val name_display_full: String,
    val team_code: String,
    val team_id: String,
    val address: String,
    val website_url: String
)