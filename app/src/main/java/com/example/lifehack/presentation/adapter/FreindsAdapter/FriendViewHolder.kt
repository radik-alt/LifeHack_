package com.example.lifehack.presentation.adapter.FreindsAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.databinding.ItemFriendsBinding

class FriendViewHolder(binding: ItemFriendsBinding) : RecyclerView.ViewHolder(binding.root) {

    val name = binding.nameFriends
    val delete = binding.deleteFriends

}