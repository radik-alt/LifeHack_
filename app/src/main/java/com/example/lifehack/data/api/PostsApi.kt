package com.example.lifehack.data.api

import com.example.lifehack.data.Utils.CREATE_UPDATE_POST
import com.example.lifehack.data.Utils.URL_POST_MAIN
import com.example.lifehack.data.entity.Comments.Comments
import com.example.lifehack.data.entity.Posts.MainPost
import com.example.lifehack.data.entity.Posts.OnePost.CreatePost
import com.example.lifehack.data.entity.Posts.OnePost.RequestCreatePost.RequestCreatePost
import retrofit2.Response
import retrofit2.http.*

interface PostsApi {

    @GET(URL_POST_MAIN)
    suspend fun getMainPost(
        @Header("Authorization") Bearer: String
    ) : Response<MainPost>


    @POST(CREATE_UPDATE_POST)
    suspend fun createPost(
        @Header("Authorization") Bearer: String,
        @Body post: CreatePost
    )

    @DELETE("post/{postId}")
    suspend fun deletePost(
        @Path("postId") userId:String,
        @Header("Authorization") Bearer: String,
    )

}