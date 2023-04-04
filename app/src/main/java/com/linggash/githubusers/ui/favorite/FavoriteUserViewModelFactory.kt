package com.linggash.githubusers.ui.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.linggash.githubusers.data.UserRepository
import com.linggash.githubusers.di.Injection

class FavoriteUserViewModelFactory private constructor(private val newsRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            return FavoriteUserViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavoriteUserViewModelFactory? = null
        fun getInstance(context: Context): FavoriteUserViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavoriteUserViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}
