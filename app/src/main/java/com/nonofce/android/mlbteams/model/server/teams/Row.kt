package com.nonofce.android.mlbteams.model.server.teams

import android.os.Parcelable
import com.nonofce.android.mlbteams.model.database.Team
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Row(
    val address_zip: String,
    val name_display_full: String,
    val team_code: String,
    val team_id: String,
    val address: String,
    val website_url: String
) : Parcelable


fun Row.convertToDb(season: String) = Team(
    season, address_zip, name_display_full, team_code, team_id, address, website_url
)