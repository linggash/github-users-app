package com.linggash.githubusers

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getUsers(
        @Query("q") username: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetail>
}