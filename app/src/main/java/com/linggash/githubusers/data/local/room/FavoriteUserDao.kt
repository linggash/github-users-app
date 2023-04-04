package com.linggash.githubusers.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.linggash.githubusers.data.local.entity.FavoriteUserEntity

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: FavoriteUserEntity)

    @Delete
    fun delete(note: FavoriteUserEntity)

    @Query("SELECT * from favorite_user")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUserEntity>>

    @Query("SELECT * FROM favorite_user WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity>
}