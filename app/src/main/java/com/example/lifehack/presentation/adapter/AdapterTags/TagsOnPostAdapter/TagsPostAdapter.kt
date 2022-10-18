package com.example.lifehack.presentation.adapter.AdapterTags.TagsOnPostAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.R
import com.example.lifehack.databinding.ItemPostTagBinding

class TagsPostAdapter(
    private val tags: List<String>
) : RecyclerView.Adapter<TagsPostViewHolder>(){

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsPostViewHolder {
        context = parent.context
        val view = ItemPostTagBinding.inflate(LayoutInflater.from(context), parent, false)
        return TagsPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagsPostViewHolder, position: Int) {

        holder.nameTags.text = tags[position]
        when (tags[position]){
            plantsTag -> holder.nameTags.background = ContextCompat.getDrawable(context, R.drawable.house_tag)
            fotoTag -> holder.nameTags.background = ContextCompat.getDrawable(context, R.drawable.eat_tag)
//            "" -> holder.nameTags.background = ContextCompat.getDrawable(context, R.drawable.sport_tag)
//            "" -> holder.nameTags.background = ContextCompat.getDrawable(context, R.drawable.children_tag)
            turism -> holder.nameTags.background = ContextCompat.getDrawable(context, R.drawable.tech_tag)
        }

    }

    override fun getItemCount(): Int = tags.size

    companion object{
        private val fotoTag = "foto"
        private val plantsTag = "plants"
        private val turism = "turism"
    }

}