package com.example.lifehack.presentation.adapter.AdapterComments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.data.entity.Comments.Comments
import com.example.lifehack.data.entity.Comments.Content
import com.example.lifehack.data.entity.Comments.Data
import com.example.lifehack.databinding.ItemCommentsBinding
import com.example.lifehack.presentation.adapter.AdapterMyLifeHacks.DiffUtilsMyLifeHack
import com.example.lifehack.presentation.adapter.intreface.OnClickComment

class AdapterComments(
    private val userId : String,
    private val onClickComment: OnClickComment
) : RecyclerView.Adapter<CommentsViewHolder>(){

    private val listComment = ArrayList<Data>()
    private var editComment = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = ItemCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {

        holder.comment.text = listComment[position].comment
        holder.userComment.text = listComment[position].author_name

        if (editComment)
            holder.delete.visibility = View.VISIBLE
        else
            holder.delete.visibility = View.GONE


        holder.itemView.setOnClickListener {
            onClickComment.onClickComment(listComment[position], 0)
        }

        holder.delete.setOnClickListener {
            onClickComment.onClickComment(listComment[position], 2)
        }

        holder.itemView.setOnLongClickListener {
            onClickComment.onClickComment(listComment[position], 1)
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listComment[position].author_id == userId){
            editComment = true
            EDIT
        } else {
            editComment = false
            NO_EDIT
        }
    }

    fun setData(listComment: List<Data>){
        val diffUtil = DiffUtilsComments(this.listComment, listComment)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        diffResult.dispatchUpdatesTo(this)
        this.listComment.addAll(listComment)
    }

    companion object{
        private const val EDIT = 1
        private const val NO_EDIT = 0
    }

    override fun getItemCount(): Int = listComment.size
}