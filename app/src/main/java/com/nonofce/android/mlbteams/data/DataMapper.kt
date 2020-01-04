package com.nonofce.android.mlbteams.data

import com.nonofce.android.domain.Player as DomainPlayer
import com.nonofce.android.domain.PlayerRoster as DomainPlayerRoster
import com.nonofce.android.domain.Team as DomainTeam
import com.nonofce.android.mlbteams.data.database.model.PlayerDetail as LocalPlayer
import com.nonofce.android.mlbteams.data.database.model.PlayerRoster as LocalPlayerRoster
import com.nonofce.android.mlbteams.data.database.model.Team as LocalTeam
import com.nonofce.android.mlbteams.data.server.model.player.Row as RemotePlayer
import com.nonofce.android.mlbteams.data.server.model.teams.Row as RemoteTeam

fun DomainTeam.toLocal(season: String): LocalTeam =
    LocalTeam(season, address_zip, name_display_full, team_code, team_id, address, website_url)

fun DomainTeam.toRemote(): RemoteTeam =
    RemoteTeam(address_zip, name_display_full, team_code, team_id, address, website_url)

fun DomainPlayerRoster.toLocal(season: String): LocalPlayerRoster = LocalPlayerRoster(
    player_id,
    bats,
    name_first_last,
    primary_position,
    roster_years,
    team_id,
    throws,
    season
)

fun DomainPlayer.toLocal(): LocalPlayer = LocalPlayer(
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

fun DomainPlayer.toRemote(): RemotePlayer = RemotePlayer(
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