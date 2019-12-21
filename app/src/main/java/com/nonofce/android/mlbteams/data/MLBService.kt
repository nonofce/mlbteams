package com.nonofce.android.mlbteams.data

import com.nonofce.android.mlbteams.model.player.PlayerResults
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
                "&roster_team_alltime.col_in=team_id" +
                "&roster_team_alltime.col_in=player_id"
    )
    fun getRosterByTeam(
        @Query("start_season") startSeason: String, @Query("end_season") endSeason: String, @Query(
            "team_id"
        ) teamId: String
    ): Deferred<RosterResults>


    @GET(
        "named.player_info.bam?sport_code='mlb'" +
                "&player_info.col_in=name_display_first_last" +
                "&player_info.col_in=birth_country" +
                "&player_info.col_in=height_inches" +
                "&player_info.col_in=height_feet" +
                "&player_info.col_in=gender" +
                "&player_info.col_in=age" +
                "&player_info.col_in=pro_debut_date" +
                "&player_info.col_in=primary_position_txt" +
                "&player_info.col_in=weight" +
                "&player_info.col_in=jersey_number" +
                "&player_info.col_in=birth_city" +
                "&player_info.col_in=twitter_id"
    )
    fun getPlayerInfo(@Query("player_id") playerId: String): Deferred<PlayerResults>
}