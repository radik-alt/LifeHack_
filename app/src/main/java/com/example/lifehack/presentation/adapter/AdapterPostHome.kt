package com.example.lifehack.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.data.entity.Posts.Content
import com.example.lifehack.databinding.ItemPostBinding
import com.example.lifehack.presentation.adapter.AdapterTags.TagsAdapters
import com.example.lifehack.presentation.adapter.AdapterTags.TagsOnPostAdapter.TagsPostAdapter
import com.example.lifehack.presentation.adapter.intreface.OnClickPost
import com.example.lifehack.presentation.adapter.intreface.OnClickTags

class AdapterPostHome(
    private val listPost: ArrayList<Content>,
    private val onClickPost: OnClickPost
) : RecyclerView.Adapter<ViewHolderPostHome>() {

    private lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPostHome {
        context = parent.context
        return ViewHolderPostHome(ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderPostHome, position: Int) {

        holder.title.text = listPost[position].title
        holder.desc.text = listPost[position].description
        holder.stars.text = listPost[position].countStar.toString()

        holder.itemView.setOnClickListener {
            onClickPost.selectItemPost(listPost[position])
        }

        val tags = listPost[position].tags
        if (tags?.isNotEmpty() == true){
            holder.tagsAdapter.adapter = TagsPostAdapter(tags)
            holder.tagsAdapter.layoutManager = GridLayoutManager(context, 3)
        }
    }

    override fun getItemCount(): Int = listPost.size
}