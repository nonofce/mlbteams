package com.nonofce.android.mlbteams.data

import com.nonofce.android.mlbteams.model.roster.RosterResults
import com.nonofce.android.mlbteams.model.teams.TeamsResults
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface MLBService {

    @GET(
        "named.team_all_season.bam?sport_code='mlb'&all_star_sw='N'&sort_order=name_asc" +
                "&team_all_season.col_in=name_display_full" +
                "&team_all_season.col_in=address_zip" +
                "&team_all_season.col_in=team_code" +
                "&team_all_season.col_in=team_id" +
                "&team_all_season.col_in=address" +
                "&team_all_season.col_in=website_url"
    )
    fun getTeamsBySeason(@Query("season") season: String): Deferred<TeamsResults>

    @GET(
        "named.roster_team_alltime.bam?" +
                "roster_team_alltime.col_in=name_first_last" +
                "&roster_team_alltime.col_in=primary_position" +
                "&roster_team_alltime.col_in=throws" +
                "&roster_team_alltime.col_in=bats" +
                "&roster_team_alltime.col_in=roster_years" +
                "&roster_team_alltime.col_in=team_id"
    )
    fun getRosterByTeam(
        @Query("start_season") startSeason: String, @Query("end_season") endSeason: String, @Query(
            "team_id"
        ) teamId: String
    ): Deferred<RosterResults>
}