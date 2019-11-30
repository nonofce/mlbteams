package com.nonofce.android.mlbteams.model

import com.nonofce.android.mlbteams.model.results.teams.TeamsResults
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface MLBService {

    @GET("named.team_all_season.bam?sport_code='mlb'&all_star_sw='N'&sort_order=name_asc&team_all_season.col_in=name_display_full&team_all_season.col_in=address_zip&team_all_season.col_in=team_code&team_all_season.col_in=team_id")
    fun getTeamsBySeason(@Query("season") season: String): Deferred<TeamsResults>
}