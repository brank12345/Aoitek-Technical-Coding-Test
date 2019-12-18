package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_info_layout.view.*

class UserListAdapter(private val userList: MutableList<UserInfo>) : RecyclerView.Adapter<UserListAdapter.MyViewHolder>() {
    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_info_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.name.text = userList[position].name
        val imageView = holder.view.image
        imageView?.apply {
            ImageUtil.loadImage(userList[position].imageUrl, imageView)
        }
    }
}