package com.example.lifehack.presentation.adapter.AdapterComments

import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.databinding.ItemCommentsBinding

class CommentsViewHolder(binding: ItemCommentsBinding) : RecyclerView.ViewHolder(binding.root){

    val userComment = binding.userComment
    val dateComment = binding.dateComment
    val comment = binding.comment

}