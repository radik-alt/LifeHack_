package com.example.lifehack.presentation.adapter.AdapterComments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.data.entity.Comments.Comments
import com.example.lifehack.data.entity.Comments.Content
import com.example.lifehack.data.entity.Comments.Data
import com.example.lifehack.databinding.ItemCommentsBinding
import com.example.lifehack.presentation.adapter.intreface.OnClickComment

class AdapterComments(
    private val listComment: List<Data>,
    private val onClickComment: OnClickComment
) : RecyclerView.Adapter<CommentsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = ItemCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {

        holder.comment.text = listComment[position].comment
        holder.userComment.text = listComment[position].author_name

        holder.itemView.setOnClickListener {
            onClickComment.onClickComment(listComment[position], 0)
        }

        holder.itemView.setOnLongClickListener {
            onClickComment.onClickComment(listComment[position], 1)
            true
        }
    }

    override fun getItemCount(): Int = listComment.size
}