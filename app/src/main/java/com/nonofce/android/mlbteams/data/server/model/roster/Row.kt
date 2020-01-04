package com.nonofce.android.mlbteams.data.server.model.roster

import android.os.Parcelable
import com.nonofce.android.mlbteams.data.database.model.PlayerRoster
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
