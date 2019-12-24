package com.nonofce.android.mlbteams.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nonofce.android.mlbteams.model.server.player.Row

@Entity
data class PlayerDetail(
    @PrimaryKey(autoGenerate = false)
    val player_id: String,
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
)

fun PlayerDetail.convertToUi() = Row(
    age,
    birth_city,
    birth_country,
    gender,
    height_feet,
    height_inches,
    jersey_number,
    name_display_first_last,
    pro_debut_date,
    pro_debut_date,
    twitter_id,
    weight,
    player_id
)
