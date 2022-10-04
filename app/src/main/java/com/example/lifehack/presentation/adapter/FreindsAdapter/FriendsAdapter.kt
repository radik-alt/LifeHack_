package com.example.lifehack.presentation.adapter.FreindsAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.databinding.ItemFriendsBinding
import java.util.zip.Inflater

class FriendsAdapter(
    private val list: ArrayList<Int>
) : RecyclerView.Adapter<FriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = ItemFriendsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = list.size
}