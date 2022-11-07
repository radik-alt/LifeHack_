package com.example.lifehack.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.databinding.ItemPostBinding

class ViewHolderPostHome(binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root){

    val title = binding.titlePost
    val desc = binding.descPost
    val stars = binding.starsRating
    val image = binding.imagePost
    val tagsAdapter = binding.adapterTags
}