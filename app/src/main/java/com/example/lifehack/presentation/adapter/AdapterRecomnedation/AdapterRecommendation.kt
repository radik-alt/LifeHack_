package com.example.lifehack.presentation.adapter.AdapterRecomnedation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.data.entity.Recommendation.Content
import com.example.lifehack.data.entity.Recommendation.RecommendationPosts
import com.example.lifehack.databinding.ItemPostBinding
import com.example.lifehack.databinding.ItemRecommendationPostBinding

class AdapterRecommendation(
    private val listRecommendationPosts: List<Content>
): RecyclerView.Adapter<RecommendationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = ItemRecommendationPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {

        holder.title.text = listRecommendationPosts[position].title
        holder.desc.text = listRecommendationPosts[position].description
        holder.stars.text = listRecommendationPosts[position].count_star.toString()

    }

    override fun getItemCount(): Int = listRecommendationPosts.size
}