package com.example.lifehack.data.entity.Recommendation

data class Content(
    val count_star: Int,
    val description: String,
    val post_id: String,
    val tags: List<String>,
    val title: String,
    val user_id: String
)