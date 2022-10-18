package com.example.lifehack.presentation.adapter.AdapterTop100

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.data.entity.Stars.Top100.Top100
import com.example.lifehack.data.entity.Stars.Top100.Top100Item
import com.example.lifehack.databinding.ItemTopPostBinding
import com.example.lifehack.presentation.adapter.intreface.OnClickTopPost

class AdapterTop100(
    private val listTop100: ArrayList<Top100Item>,
    private val onClickTopPost: OnClickTopPost
): RecyclerView.Adapter<ViewHolderTop100>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTop100 {
        val view = ItemTopPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderTop100(view)
    }

    override fun onBindViewHolder(holder: ViewHolderTop100, position: Int) {

        holder.title.text = listTop100[position].title
        holder.description.text = listTop100[position].description
        holder.stars.text = listTop100[position].count_star.toString()

        holder.itemView.setOnClickListener {
            onClickTopPost.clickTopPost(listTop100[position])
        }

    }

    override fun getItemCount(): Int = listTop100.size

}