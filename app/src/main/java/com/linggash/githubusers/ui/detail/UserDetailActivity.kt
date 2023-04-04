package com.linggash.githubusers.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.linggash.githubusers.R
import com.linggash.githubusers.data.remote.UserDetail
import com.linggash.githubusers.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = if (savedInstanceState != null) {
            savedInstanceState.getString(STATE_USERNAME)!!
        }else {
            intent.getStringExtra("USERNAME")!!
        }
        val userDetailViewModel = ViewModelProvider(this,
            UserDetailViewModel.UserDetailViewModelFactory(
                username
            )
        )[UserDetailViewModel::class.java]

        userDetailViewModel.userDetail.observe(this){
            setUserDetail(it)
        }

        userDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_USERNAME, username)
    }

    @SuppressLint("SetTextI18n")
    private fun setUserDetail(users: UserDetail) {
        binding.apply {
            tvDetailName.text = users.name
            tvDetailUsername.text = users.login
            tvFollower.text = users.follower + " Followers"
            tvFollowing.text = users.following + " Following"
        }
        Glide.with(this)
            .load(users.avatarUrl)
            .into(binding.imgDetailImage)
        Log.i("isi", users.toString())
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val STATE_USERNAME = "state_username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}
