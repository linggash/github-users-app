package com.linggash.githubusers.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.linggash.githubusers.data.remote.ApiConfig
import com.linggash.githubusers.data.remote.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel(
    private val name: String,
) : ViewModel() {

    private val _userFollower = MutableLiveData<List<User>>()
    val userFollower : LiveData<List<User>> = _userFollower

    private val _userFollowing = MutableLiveData<List<User>>()
    val userFollowing : LiveData<List<User>> = _userFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowViewModel"
    }

    init {
        getFollower()
        getFollowing()
    }

    private fun getFollower() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(name)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userFollower.value = responseBody
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    private fun getFollowing() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(name)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userFollowing.value = responseBody
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    class FollowViewModelFactory(
        private val name: String,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FollowViewModel(name) as T
        }
    }
}