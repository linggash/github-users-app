package com.linggash.githubusers.ui.detail

import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.linggash.githubusers.User
import com.linggash.githubusers.UserAdapter
import com.linggash.githubusers.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var position: Int
        var username: String

        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvFollow?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding?.rvFollow?.addItemDecoration(itemDecoration)

        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER)
            username = it.getString(ARG_USERNAME).toString()
            val followViewModel = ViewModelProvider(this, FollowViewModel.FollowViewModelFactory(
                username
            )
            )[FollowViewModel::class.java]
            if (position == 1){
                followViewModel.userFollower.observe(viewLifecycleOwner){follower ->
                    setFollow(follower)
                }
            } else {
                followViewModel.userFollowing.observe(viewLifecycleOwner){follower ->
                    setFollow(follower)
                }
            }
            followViewModel.isLoading.observe(requireActivity()) { loading ->
                showLoading(loading)
            }
        }
    }

    private fun setFollow(users: List<User>) {
        val listUser = ArrayList<User>()
        for (user in users) {
            listUser.add(
                User(
                    avatarUrl = user.avatarUrl,
                    login = user.login
                )
            )
        }
        val adapter = UserAdapter(listUser){
            val intentDetail = Intent(requireActivity(), UserDetailActivity::class.java)
            intentDetail.putExtra("USERNAME", it.login)
            startActivity(intentDetail)
        }
        binding?.rvFollow?.adapter = adapter
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "username"
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}