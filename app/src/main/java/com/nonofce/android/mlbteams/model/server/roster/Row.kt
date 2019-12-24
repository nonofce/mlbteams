package com.nonofce.android.mlbteams.model.server.roster

import android.os.Parcelable
import com.nonofce.android.mlbteams.model.database.PlayerRoster
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Row(
    val bats: String,
    val name_first_last: String,
    val primary_position: String,
    val roster_years: String,
    val team_id: String,
    val throws: String,
    val player_id: String

) : Parcelable

fun Row.convertToDb(season: String) = PlayerRoster(
    player_id,
    bats,
    name_first_last,
    primary_position,
    roster_years,
    team_id,
    throws,
    season
)