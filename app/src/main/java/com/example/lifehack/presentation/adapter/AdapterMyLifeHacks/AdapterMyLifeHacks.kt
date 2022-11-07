package com.example.lifehack.presentation.adapter.AdapterMyLifeHacks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.data.entity.Posts.ProfilePosts.Content
import com.example.lifehack.data.entity.Posts.GetPostsOfUser.Data
import com.example.lifehack.databinding.ItemPostBinding

class AdapterMyLifeHacks : RecyclerView.Adapter<MyLifeHackViewHolder>() {

    private var listPost = ArrayList<Content>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyLifeHackViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyLifeHackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyLifeHackViewHolder, position: Int) {

        holder.title.text = listPost[position].title
        holder.desc.text = listPost[position].description

    }

    override fun getItemCount(): Int = listPost.size

    fun setData(listPost: List<Content>){
        val diffUtil = DiffUtilsMyLifeHack(this.listPost, listPost)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        diffResult.dispatchUpdatesTo(this)
        this.listPost.addAll(listPost)
    }
}