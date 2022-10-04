package com.example.lifehack.data.entity.Posts

data class Content(
    val countStar: Int,
    val description: String,
    val tags: List<String>,
    val title: String
)