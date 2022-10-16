package com.example.lifehack.data.entity.Posts.OnePost.RequestCreatePost

data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val sort: Sort,
    val unpaged: Boolean
)