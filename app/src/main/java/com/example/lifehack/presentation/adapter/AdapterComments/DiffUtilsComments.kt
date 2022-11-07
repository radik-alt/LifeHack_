package com.example.lifehack.presentation.adapter.AdapterComments

import androidx.recyclerview.widget.DiffUtil
import com.example.lifehack.data.entity.Comments.Data

class DiffUtilsComments(
    private val oldListComments: List<Data>,
    private val newListComments: List<Data>
) :DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldListComments.size

    override fun getNewListSize(): Int = newListComments.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListComments[oldItemPosition].id == newListComments[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListComments[oldItemPosition] == newListComments[newItemPosition]
    }
}