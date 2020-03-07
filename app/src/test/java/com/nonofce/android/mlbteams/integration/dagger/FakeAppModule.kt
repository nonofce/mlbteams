package com.nonofce.android.mlbteams.integration.dagger

import com.nonofce.android.data.source.*
import com.nonofce.android.domain.Player
import com.nonofce.android.domain.PlayerRoster
import com.nonofce.android.domain.Team
import com.nonofce.android.domain.nullTeam
import com.nonofce.android.mlbteams.data.database.toDomain
import com.nonofce.android.mlbteams.data.toLocal
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.nonofce.android.mlbteams.data.database.model.PlayerDetail as LocalPlayer
import com.nonofce.android.mlbteams.data.database.model.PlayerRoster as LocalPlayerRoster
import com.nonofce.android.mlbteams.data.database.model.Team as LocalTeam

@Module
class FakeAppModule {

    @Provides
    @Singleton
    fun localDataSourceProvider(): LocalDataSource =
        FakeLocalDataSource()

    @Provides
    @Singleton
    fun remoteDataSourceProvider(): RemoteDataSource =
        FakeRemoteDataSource()

    @Provides
    @Singleton
    fun getLocationDataSourceProvider(): LocationDataSource =
        FakeLocationDataSource()

    @Provides
    @Singleton
    fun getPermissionChecker(): PermissionChecker =
        FakePermissionChecker()
}

class FakePermissionChecker : PermissionChecker {
    override suspend fun check(permission: PermissionChecker.Permission): Boolean = true
}

class FakeLocationDataSource : LocationDataSource {
    override suspend fun getZipCode(): String? = "33019"
}

class FakeLocalDataSource : LocalDataSource {

    var fakeLocalTeams: List<LocalTeam> = emptyList()
    var fakeLocalRoster: List<LocalPlayerRoster> = emptyList()
    var fakeLocalPlayer: List<LocalPlayer> = emptyList()

    override suspend fun existsTeamsForSeason(season: String): Boolean =
        fakeLocalTeams.filter { it.season == season }.isNotEmpty()

    override suspend fun loadTeamsBySeason(season: String): List<Team> =
        fakeLocalTeams.filter { it.season == season }.map { it.toDomain() }

    override suspend fun saveTeams(season: String, teams: List<Team>) {
        fakeLocalTeams = teams.map { it.toLocal(season) }
    }

    override suspend fun deleteTeamsInSeason(season: String): Int {
        fakeLocalTeams = fakeLocalTeams.filterNot { it.season == season }
        return fakeLocalTeams.count()
    }

    override suspend fun loadRosterByTeamAndSeason(
        teamId: String,
        season: String
    ): List<PlayerRoster> =
        fakeLocalRoster.filter { it.team_id == teamId && it.season == season }.map { it.toDomain() }

    override suspend fun existsRosterForTeamAndSeason(teamId: String, season: String): Boolean =
        fakeLocalRoster.filter { it.team_id == teamId && it.season == season }.isNotEmpty()

    override suspend fun saveRoster(season: String, roster: List<PlayerRoster>) {
        fakeLocalRoster = roster.map { it.toLocal(season) }
    }

    override suspend fun deleteTeamRosterForSeason(teamId: String, season: String): Int {
        fakeLocalRoster = fakeLocalRoster.filterNot { it.team_id == teamId && it.season == season }
        return fakeLocalRoster.count()
    }

    override suspend fun existsPlayer(playerId: String): Boolean =
        fakeLocalPlayer.filter { it.player_id == playerId }.isNotEmpty()

    override suspend fun loadPlayerInfo(playerId: String): Player =
        fakeLocalPlayer.find { it.player_id == playerId }?.toDomain() ?: fakePlayer

    override suspend fun savePlayer(player: Player) {
        fakeLocalPlayer =
            fakeLocalPlayer.filterNot { it.player_id == player.player_id } + player.toLocal()
    }

    override suspend fun deletePlayer(playerId: String): Int {
        fakeLocalPlayer = fakeLocalPlayer.filterNot { it.player_id == playerId }
        return fakeLocalPlayer.count()
    }

    override suspend fun getLocalTeam(season: String, zipCode: String): Team = nullTeam

}

class FakeRemoteDataSource : RemoteDataSource {
    override suspend fun loadTeamsBySeason(season: String): Result<List<Team>> = Result.Success(
        fakeTeams
    )

    override suspend fun loadRosterByTeamAndSeason(
        season: String,
        teamId: String
    ): Result<List<PlayerRoster>> = Result.Success(fakeRoster)

    override suspend fun loadPlayerInfo(playerId: String): Player =
        fakePlayer

}
