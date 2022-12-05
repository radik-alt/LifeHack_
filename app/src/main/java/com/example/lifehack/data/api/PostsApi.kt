package com.example.lifehack.data.api

import com.example.lifehack.data.Utils.CREATE_UPDATE_POST
import com.example.lifehack.data.Utils.URL_POST_MAIN
import com.example.lifehack.data.entity.Comments.Comments
import com.example.lifehack.data.entity.Posts.GetPostsOfUser.PostsOfUser
import com.example.lifehack.data.entity.Posts.MainPost
import com.example.lifehack.data.entity.Posts.OnePost.CreatePost
import com.example.lifehack.data.entity.Posts.OnePost.RequestCreatePost.RequestCreatePost
import com.example.lifehack.data.entity.Posts.ProfilePosts.PostsUserProfile
import com.example.lifehack.data.entity.Posts.UpdatePost.UpdatePost
import com.example.lifehack.data.entity.Recommendation.RecommendationPosts
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

    @POST(CREATE_UPDATE_POST)
    suspend fun updatePost(
        @Header("Authorization") Bearer: String,
        @Body updatePost: UpdatePost
    )


    @GET("user/{userId}/posts")
    suspend fun getPostsOfUserProfile(
        @Path("userId") userId:String,
        @Header("Authorization") Bearer: String,
    ):Response<PostsUserProfile>


    @DELETE("post/{postId}")
    suspend fun deletePost(
        @Path("postId") userId:String,
        @Header("Authorization") Bearer: String,
    )

    @GET("post/recommendation?page=0&size=5&sort=id,desc")
    suspend fun getRecommendationPosts(
        @Header("Authorization") Bearer: String,
    ):Response<RecommendationPosts>

}