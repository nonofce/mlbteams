package com.nonofce.android.mlbteams.model.server.player

import android.os.Parcelable
import com.nonofce.android.mlbteams.model.database.PlayerDetail
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
    val weight: String,
    val player_id: String
) : Parcelable

fun Row.convertToDb() = PlayerDetail(
    player_id,
    age,
    birth_city,
    birth_country,
    gender,
    height_feet,
    height_inches,
    jersey_number,
    name_display_first_last,
    primary_position_txt,
    pro_debut_date,
    twitter_id,
    weight
)