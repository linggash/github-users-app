package com.linggash.githubusers.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.linggash.githubusers.databinding.ActivityFavoriteUserBinding
import com.linggash.githubusers.ui.UserAdapter
import com.linggash.githubusers.data.local.entity.FavoriteUserEntity
import com.linggash.githubusers.data.remote.User
import com.linggash.githubusers.ui.detail.UserDetailActivity

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite Github User"

        val factory: FavoriteUserViewModelFactory = FavoriteUserViewModelFactory.getInstance(this)
        val viewModel: FavoriteUserViewModel by viewModels {
            factory
        }

        viewModel.getFavoriteUser().observe(this) {
            setListUserData(it)
        }
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
    }

    private fun setListUserData(users: List<FavoriteUserEntity>) {
        val listUser = ArrayList<User>()
        for (user in users) {
            listUser.add(
                User(
                    avatarUrl = user.avatarUrl.toString(),
                    login = user.username
                )
            )
        }
        val adapter = UserAdapter(listUser){
            val intentDetail = Intent(this, UserDetailActivity::class.java)
            intentDetail.putExtra("USERNAME", it.login)
            startActivity(intentDetail)
        }
        binding.rvUsers.adapter = adapter
    }
}