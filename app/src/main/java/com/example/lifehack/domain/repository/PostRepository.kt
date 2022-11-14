package com.example.lifehack.domain.repository

import com.example.lifehack.data.entity.Posts.MainPost
import com.example.lifehack.data.entity.Posts.OnePost.CreatePost
import com.example.lifehack.data.entity.Posts.ProfilePosts.PostsUserProfile
import retrofit2.Response

interface PostRepository {

    suspend fun getMainPost(bearer: String): Response<MainPost>

    suspend fun getPostsUserProfile(userId: String, bearer: String):Response<PostsUserProfile>

    suspend fun createPost(post: CreatePost, bearer: String)

    suspend fun deletePost(postId:String, bearer: String)
}