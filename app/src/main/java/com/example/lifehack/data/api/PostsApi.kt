package com.example.lifehack.data.api

import com.example.lifehack.data.entity.Auth.ImageUser
import com.example.lifehack.data.entity.Posts.MainPost
import com.example.lifehack.data.entity.Posts.OnePost.CreatePost
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PostsApi {

    @GET("post/main")
    suspend fun getMainPost(
        @Header("Authorization") Bearer: String
    ) : Response<MainPost>

    @GET()
    suspend fun getImageUser(

    ): Response<ImageUser>


    @POST("post")
    suspend fun createPost(
        @Header("Authorization") Bearer: String,
        @Body post: CreatePost
    ) : Response<String>
}