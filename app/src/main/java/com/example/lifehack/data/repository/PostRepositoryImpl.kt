package com.example.lifehack.data.repository

import com.example.lifehack.data.api.ApiDataConnect
import com.example.lifehack.data.entity.Posts.MainPost
import com.example.lifehack.data.entity.Posts.OnePost.CreatePost
import com.example.lifehack.data.entity.Posts.ProfilePosts.PostsUserProfile
import com.example.lifehack.domain.repository.PostRepository
import retrofit2.Response

class PostRepositoryImpl : PostRepository {

    override suspend fun getMainPost(bearer: String): Response<MainPost> {
        return ApiDataConnect.API_POSTS.getMainPost(bearer)
    }

    override suspend fun getPostsUserProfile(
        userId: String,
        bearer: String
    ): Response<PostsUserProfile> {
        return ApiDataConnect.API_POSTS.getPostsOfUserProfile(userId, bearer)
    }

    override suspend fun createPost(post: CreatePost, bearer: String) {
        ApiDataConnect.API_POSTS.createPost(bearer, post)
    }

    override suspend fun deletePost(postId: String, bearer: String) {
        ApiDataConnect.API_POSTS.deletePost(postId, bearer)
    }
}