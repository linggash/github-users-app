package com.linggash.githubusers.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.linggash.githubusers.data.local.entity.FavoriteUserEntity
import com.linggash.githubusers.data.local.room.FavoriteUserDao
import com.linggash.githubusers.data.remote.ApiService
import com.linggash.githubusers.utils.AppExecutors

class UserRepository private constructor(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao,
    private val appExecutors: AppExecutors
){
    private val result = MediatorLiveData<Result<List<FavoriteUserEntity>>>()

    fun setFavoriteUser(user: FavoriteUserEntity) {
        appExecutors.diskIO.execute {
            favoriteUserDao.insert(user)
        }
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUserEntity>> {
        return favoriteUserDao.getAllFavoriteUser()
    }

    fun removeFavoriteUser(user: FavoriteUserEntity) {
        appExecutors.diskIO.execute {
            favoriteUserDao.delete(user)
        }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity>  {
        return favoriteUserDao.getFavoriteUserByUsername(username)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: FavoriteUserDao,
            appExecutors: AppExecutors
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, newsDao, appExecutors)
            }.also { instance = it }
    }
}