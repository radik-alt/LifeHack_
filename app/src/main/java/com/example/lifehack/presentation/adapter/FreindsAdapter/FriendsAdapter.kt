package com.example.lifehack.presentation.adapter.FreindsAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.data.entity.Follow.Data
import com.example.lifehack.databinding.ItemFriendsBinding
import java.util.zip.Inflater

class FriendsAdapter(
    private val list: List<Data>
) : RecyclerView.Adapter<FriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = ItemFriendsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {

        holder.name.text = list[position].followedName

        holder.delete.setOnClickListener {
            Log.d("DeleteFriends", "${list[position].id} ${list[position].followedId}")
        }
    }

    override fun getItemCount(): Int = list.size
}