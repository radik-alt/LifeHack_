package com.example.lifehack.presentation.adapter.AdapterTags.TagsOnPostAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.databinding.ItemPostTagBinding

class TagsPostAdapter(
    private val tags: List<String>
) : RecyclerView.Adapter<TagsPostViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsPostViewHolder {
        val view = ItemPostTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagsPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagsPostViewHolder, position: Int) {

        holder.nameTags.text = tags[position]

    }

    override fun getItemCount(): Int = tags.size
}