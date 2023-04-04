package com.linggash.githubusers.di

import android.content.Context
import com.linggash.githubusers.data.UserRepository
import com.linggash.githubusers.data.local.room.UserRoomDatabase
import com.linggash.githubusers.data.remote.ApiConfig
import com.linggash.githubusers.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserRoomDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()
        val appExecutors = AppExecutors()
        return UserRepository.getInstance(apiService, dao, appExecutors)
    }
}