package com.nonofce.android.mlbteams.model.player

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Row(
    val age: String,
    val birth_city: String,
    val birth_country: String,
    val gender: String,
    val height_feet: String,
    val height_inches: String,
    val jersey_number: String,
    val name_display_first_last: String,
    val primary_position_txt: String,
    val pro_debut_date: String,
    val twitter_id: String,
    val weight: String
) : Parcelable