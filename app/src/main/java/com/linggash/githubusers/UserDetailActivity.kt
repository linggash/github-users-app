package com.linggash.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.linggash.githubusers.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var username: String
//    private val userDetailViewModel by viewModels<UserDetailViewModel>()

    companion object {
        private const val STATE_USERNAME = "state_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            username = savedInstanceState.getString(STATE_USERNAME)!!
        }else {
            username = intent.getStringExtra("USERNAME")!!
        }
        val userDetailViewModel = ViewModelProvider(this, UserDetailViewModel.UserDetailViewModelFactory(username!!))[UserDetailViewModel::class.java]

        userDetailViewModel.userDetail.observe(this){
            setUserDetail(it)
        }

        userDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_USERNAME, username)
    }

    private fun setUserDetail(users: UserDetail) {
        binding.tvDetailName.text = users.name
        binding.tvDetailUsername.text = users.login
        Glide.with(this)
            .load(users.avatarUrl)
            .into(binding.imgDetailImage)
        Log.i("isi", users.toString())
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}