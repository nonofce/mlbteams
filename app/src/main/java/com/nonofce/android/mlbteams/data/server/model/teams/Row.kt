package com.nonofce.android.mlbteams.data.server.model.teams

import android.os.Parcelable
import com.nonofce.android.mlbteams.data.database.model.Team
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
