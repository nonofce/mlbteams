package com.nonofce.android.mlbteams.integration.dagger

import com.nonofce.android.commons.mockedPlayer
import com.nonofce.android.commons.mockedRoster
import com.nonofce.android.commons.mockedTeam

val fakeTeams = listOf(
    mockedTeam.copy(name_display_full = "Team 1", team_code = "T1", team_id = "1")
)

val fakeRoster = listOf(
    mockedRoster.copy(name_first_last = "Player 1", player_id = "1")
)

val fakePlayer = mockedPlayer.copy(name_display_first_last = "Player 1", player_id = "1")