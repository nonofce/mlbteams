package com.nonofce.android.mlbteams.data.database.model

import androidx.room.Entity
import com.nonofce.android.mlbteams.data.server.model.roster.Row

@Entity(primaryKeys = ["season", "team_id", "player_id"])
data class PlayerRoster(
    val player_id: String,
    val bats: String,
    val name_first_last: String,
    val primary_position: String,
    val roster_years: String,
    val team_id: String,
    val throws_hand: String,
    val season: String
)
