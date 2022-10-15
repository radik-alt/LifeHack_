package com.example.lifehack.presentation.adapter.FreindsAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.data.entity.Follow.Data
import com.example.lifehack.databinding.ItemFriendsBinding
import com.example.lifehack.presentation.adapter.intreface.OnClickFollower
import java.util.zip.Inflater

class FriendsAdapter(
    private val list: List<Data>,
    private val onClickFollower: OnClickFollower
) : RecyclerView.Adapter<FriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = ItemFriendsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {

        holder.name.text = list[position].followedName

        holder.itemView.setOnClickListener {
            onClickFollower.onClickFollower(list[position], false)
        }

        holder.delete.setOnClickListener {
            onClickFollower.onClickFollower(list[position], true)
        }
    }

    override fun getItemCount(): Int = list.size
}