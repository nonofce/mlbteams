package com.nonofce.android.mlbteams.data.server

import com.nonofce.android.domain.Player as DomainPlayer
import com.nonofce.android.domain.PlayerRoster as DomainPlayerRoster
import com.nonofce.android.domain.Team as DomainTeam
import com.nonofce.android.mlbteams.data.server.model.player.Row as RemotePlayer
import com.nonofce.android.mlbteams.data.server.model.roster.Row as RemotePlayerRoster
import com.nonofce.android.mlbteams.data.server.model.teams.Row as RemoteTeam

fun RemoteTeam.toDomain(): DomainTeam =
    DomainTeam(address_zip, name_display_full, team_code, team_id, address, website_url)

fun RemotePlayerRoster.toDomain(): DomainPlayerRoster =
    DomainPlayerRoster(
        bats,
        name_first_last,
        primary_position,
        roster_years,
        team_id,
        throws,
        player_id
    )

fun RemotePlayer.toDomain(): DomainPlayer = DomainPlayer(
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