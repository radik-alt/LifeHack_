package com.example.lifehack.presentation.adapter.AdapterTags

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.R
import com.example.lifehack.databinding.ItemPostBinding
import com.example.lifehack.databinding.ItemTagsBinding

class TagsAdapters(
    private val tags: ArrayList<String>
) : RecyclerView.Adapter<TagsViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        context = parent.context
        val view = ItemTagsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {

        holder.button.text = tags[position]

        when (position){
//            0 -> holder.button.background = ContextCompat.getDrawable(context, R.drawable.button)
        }
    }

    override fun getItemCount(): Int = tags.size
}