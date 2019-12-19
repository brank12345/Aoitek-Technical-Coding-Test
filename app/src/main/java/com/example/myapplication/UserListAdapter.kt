package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_info_layout.view.*

class UserListAdapter(val userList: MutableList<UserInfo>, private val listener: ReachNearestBottomListener) : RecyclerView.Adapter<UserListAdapter.MyViewHolder>() {
    interface ReachNearestBottomListener {
        fun reachNearestBottom()
    }

    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = when(viewType) {
            ViewType.NORMAL.value,
            ViewType.SPECIAL.value -> {
                LayoutInflater.from(parent.context).inflate(R.layout.user_info_layout, parent, false)
            }
            ViewType.PROGRESS.value -> {
                LayoutInflater.from(parent.context).inflate(R.layout.progress_bar_layout, parent, false)
            }
            else -> {
                LayoutInflater.from(parent.context).inflate(R.layout.user_info_layout, parent, false)
            }
        }
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when(getItemViewType(position)) {
            ViewType.NORMAL.value,
            ViewType.SPECIAL.value -> {
                holder.view.name.text = userList[position].name
                val imageView = holder.view.image
                imageView?.apply {
                    ImageUtil.loadImage(userList[position].imageUrl, imageView)
                }
            }
            ViewType.PROGRESS.value -> Unit
            else -> Unit
        }

        if (position == userList.size - 4) {
            listener.reachNearestBottom()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position%7 == 0 -> ViewType.SPECIAL.value
            position == userList.size-1 -> ViewType.PROGRESS.value
            else -> ViewType.NORMAL.value
        }
    }
}