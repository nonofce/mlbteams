package com.nonofce.android.mlbteams.model.server.roster

import android.os.Parcelable
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