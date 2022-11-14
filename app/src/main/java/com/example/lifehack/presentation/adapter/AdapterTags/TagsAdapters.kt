package com.example.lifehack.presentation.adapter.AdapterTags

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.R
import com.example.lifehack.data.entity.Tags.GetListTagsItem
import com.example.lifehack.data.entity.TagsDTO
import com.example.lifehack.databinding.ItemTagsBinding
import com.example.lifehack.presentation.adapter.intreface.OnClickTags

class TagsAdapters(
    private val tags: ArrayList<GetListTagsItem>,
    private val onClickTags: OnClickTags
) : RecyclerView.Adapter<TagsViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        context = parent.context
        val view = ItemTagsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {

        holder.button.text = tags[position].title

//        when (position){
//            0 -> holder.button.background = ContextCompat.getDrawable(context, R.drawable.house_tag)
//            1 -> holder.button.background = ContextCompat.getDrawable(context, R.drawable.eat_tag)
//            2 -> holder.button.background = ContextCompat.getDrawable(context, R.drawable.sport_tag)
//            3 -> holder.button.background = ContextCompat.getDrawable(context, R.drawable.children_tag)
//            4 -> holder.button.background = ContextCompat.getDrawable(context, R.drawable.tech_tag)
//        }

        holder.button.setOnClickListener {
//            selectTags(holder, position)
        }
    }

    private fun selectTags(holder: TagsViewHolder, position: Int){
        when (position){
            0 -> {
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.click_house_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.eat_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.sport_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.children_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.tech_tag)
            }
            1 -> {
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.house_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.clcik_eat_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.sport_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.children_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.tech_tag)
            }
            2 -> {
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.house_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.eat_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.click_sport_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.children_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.tech_tag)
            }
            3 -> {
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.house_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.eat_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.sport_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.click_children_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.tech_tag)
            }
            4 -> {
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.house_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.eat_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.sport_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.children_tag)
                holder.button.background = ContextCompat.getDrawable(context, R.drawable.click_tech_tag)
            }
        }
    }


    override fun getItemCount(): Int = tags.size
}