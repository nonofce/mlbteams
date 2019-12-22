package com.nonofce.android.mlbteams.model.server.teams

import android.os.Parcelable
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