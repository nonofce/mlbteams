package com.nonofce.android.mlbteams.model.database

import androidx.room.Entity
import com.nonofce.android.mlbteams.model.server.roster.Row

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

fun PlayerRoster.convertToUi() =
    Row(bats, name_first_last, primary_position, roster_years, team_id, throws_hand, player_id)