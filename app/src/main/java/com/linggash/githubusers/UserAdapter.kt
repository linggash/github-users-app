package com.linggash.githubusers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private val listUser: List<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(listUser[position].avatarUrl.toString())
            .into(holder.imgItem)
        holder.tvItem.text = listUser[position].login
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgItem : CircleImageView = view.findViewById(R.id.img_item_image)
        val tvItem : TextView = view.findViewById(R.id.tv_item_name)
    }
}