package com.linggash.githubusers.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.linggash.githubusers.ApiConfig
import com.linggash.githubusers.UserDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(private val name: String) : ViewModel() {

    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail : LiveData<UserDetail> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "UserDetailViewModel"
    }

    init {
        getUserDetail()
    }

    private fun getUserDetail() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(name)
        client.enqueue(object : Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userDetail.value = responseBody
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    class UserDetailViewModelFactory(private val name: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserDetailViewModel(name) as T
        }
    }
}

