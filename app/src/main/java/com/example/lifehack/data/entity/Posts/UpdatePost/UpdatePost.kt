package com.example.lifehack.data.entity.Posts.UpdatePost

data class UpdatePost(
    val description: String,
    val post_id: String,
    val tags: List<String>,
    val title: String
)