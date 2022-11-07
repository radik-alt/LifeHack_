package com.example.lifehack.presentation.adapter.AdapterMyLifeHacks

import androidx.recyclerview.widget.DiffUtil
import com.example.lifehack.data.entity.Posts.ProfilePosts.Content

class DiffUtilsMyLifeHack(
    private val oldList:List<Content>,
    private val newList:List<Content>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}