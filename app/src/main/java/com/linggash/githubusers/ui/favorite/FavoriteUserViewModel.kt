package com.linggash.githubusers.ui.favorite

import androidx.lifecycle.ViewModel
import com.linggash.githubusers.data.UserRepository
import com.linggash.githubusers.data.local.entity.FavoriteUserEntity

class FavoriteUserViewModel(private val userRepository: UserRepository) : ViewModel() {

    init {
        getFavoriteUser()
    }

    fun getFavoriteUser() = userRepository.getFavoriteUser()

    fun saveFavoriteUser(user: FavoriteUserEntity) {
        userRepository.setFavoriteUser(user)
    }

    fun deleteFavoriteUser(user: FavoriteUserEntity) {
        userRepository.removeFavoriteUser(user)
    }

    fun isFavorite(username: String) = userRepository.getFavoriteUserByUsername(username)

}