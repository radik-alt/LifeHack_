package com.example.lifehack.presentation.adapter.AdapterRecomnedation

import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.databinding.ItemPostBinding
import com.example.lifehack.databinding.ItemRecommendationPostBinding

class RecommendationViewHolder(binding:ItemRecommendationPostBinding) : RecyclerView.ViewHolder(binding.root) {

    val title = binding.titlePost
    val desc = binding.descPost
    val stars = binding.starsRating
    val image = binding.imagePost

}