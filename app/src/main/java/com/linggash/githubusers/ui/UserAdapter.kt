package com.linggash.githubusers.ui

import com.linggash.githubusers.data.remote.User
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.linggash.githubusers.databinding.ItemUserBinding

class UserAdapter(
    private val listUser: List<User>,
    private val onClick: (User) -> Unit
): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(listUser[position].avatarUrl)
            .into(holder.binding.imgItemImage)
        holder.binding.tvItemName.text = listUser[position].login
        holder.bind(listUser[position])
    }
    inner class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.root.setOnClickListener {
                onClick(user)
            }
        }
    }
}