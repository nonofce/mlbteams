package com.nonofce.android.mlbteams.data.database

import com.nonofce.android.domain.Player as DomainPlayer
import com.nonofce.android.domain.PlayerRoster as DomainPlayerRoster
import com.nonofce.android.domain.Team as DomainTeam
import com.nonofce.android.mlbteams.data.database.model.PlayerDetail as LocalPlayer
import com.nonofce.android.mlbteams.data.database.model.PlayerRoster as LocalPlayerRoster
import com.nonofce.android.mlbteams.data.database.model.Team as LocalTeam

fun LocalTeam.toDomain(): DomainTeam =
    DomainTeam(address_zip, name_display_full, team_code, team_id, address, website_url)

fun LocalPlayerRoster.toDomain(): DomainPlayerRoster = DomainPlayerRoster(
    bats,
    name_first_last,
    primary_position,
    roster_years,
    team_id,
    throws_hand,
    player_id
)

fun LocalPlayer.toDomain(): DomainPlayer = DomainPlayer(
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
    weight,
    player_id
)