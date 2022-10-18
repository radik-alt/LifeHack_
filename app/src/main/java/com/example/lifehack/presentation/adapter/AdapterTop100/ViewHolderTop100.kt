package com.example.lifehack.presentation.adapter.AdapterTop100

import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.databinding.ItemTopPostBinding

class ViewHolderTop100(binding:ItemTopPostBinding): RecyclerView.ViewHolder(binding.root) {

    val title = binding.titlePostTop
    val description = binding.descPostTop
    val stars = binding.starsRatingTop

}