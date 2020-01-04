package com.nonofce.android.mlbteams.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nonofce.android.mlbteams.data.database.model.PlayerDetail

@Dao
interface PlayerDetailDao {

    @Query("SELECT * FROM PlayerDetail WHERE player_id = :playerId")
    fun getPlayerDetailById(playerId: String): PlayerDetail

    @Query("SELECT count(player_id) FROM PlayerDetail WHERE player_id = :playerId")
    fun existsPlayer(playerId: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPlayerDetail(playerDetail: PlayerDetail)

    @Query("DELETE FROM PlayerDetail WHERE player_id = :playerId")
    fun deletePlayerDetail(playerId: String): Int
}