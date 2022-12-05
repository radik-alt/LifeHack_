package com.example.lifehack.domain.repository

import com.example.lifehack.data.entity.Posts.MainPost
import com.example.lifehack.data.entity.Posts.OnePost.CreatePost
import com.example.lifehack.data.entity.Posts.ProfilePosts.PostsUserProfile
import com.example.lifehack.data.entity.Posts.UpdatePost.UpdatePost
import com.example.lifehack.data.entity.Recommendation.RecommendationPosts
import retrofit2.Response
import retrofit2.http.Header

interface PostRepository {

    suspend fun getMainPost(bearer: String): Response<MainPost>

    suspend fun getPostsUserProfile(userId: String, bearer: String):Response<PostsUserProfile>

    suspend fun createPost(post: CreatePost, bearer: String)

    suspend fun updatePost(updatePost: UpdatePost, bearer: String)

    suspend fun deletePost(postId:String, bearer: String)

    suspend fun getRecommendationPosts(Bearer: String):Response<RecommendationPosts>
}