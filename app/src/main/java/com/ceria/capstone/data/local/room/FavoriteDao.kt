package com.ceria.capstone.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun addSongToFavorite(favoriteSong: FavoriteEntity)

    @Query("DELETE FROM favorite_songs WHERE favorite_songs.id=:id")
    suspend fun removeSongFromFavorite(id: String)

    @Query("SELECT EXISTS(SELECT * FROM favorite_songs WHERE favorite_songs.id=:id)")
    suspend fun isSongFavorite(id: String): Boolean

    @Query("SELECT * FROM favorite_songs")
    suspend fun getFavoriteSongs(): List<FavoriteEntity>
}