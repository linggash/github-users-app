package com.linggash.githubusers.data.remote

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val users: List<User>
)

data class User(
	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val login: String
)

data class UserDetail(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("following")
	val following: String,

	@field:SerializedName("followers")
	val follower: String
)
