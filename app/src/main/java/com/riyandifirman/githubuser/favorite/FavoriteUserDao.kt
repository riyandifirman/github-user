package com.riyandifirman.githubuser.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.riyandifirman.githubuser.favorite.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun insertFavoriteUser(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun isFavorite(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun deleteFavoriteUser(id: Int): Int
}