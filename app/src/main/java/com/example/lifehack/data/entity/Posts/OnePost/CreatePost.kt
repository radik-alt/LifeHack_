package com.example.lifehack.data.entity.Posts.OnePost

data class CreatePost(
    val description: String,
    val tags: List<String>,
    val title: String
)