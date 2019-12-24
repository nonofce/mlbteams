package com.nonofce.android.mlbteams.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nonofce.android.mlbteams.model.server.teams.Row as ServerTeam

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

fun Team.convertToUi() =
    ServerTeam(address_zip, name_display_full, team_code, team_id, address, website_url)