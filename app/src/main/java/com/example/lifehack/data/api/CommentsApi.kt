package com.example.lifehack.data.api

import com.example.lifehack.data.entity.Comments.AddComment.AddComment
import com.example.lifehack.data.entity.Comments.ChangeComment.ChangeComment
import com.example.lifehack.data.entity.Comments.Comments
import retrofit2.Response
import retrofit2.http.*

interface CommentsApi {

    @GET("comment/post/{postId}")
    suspend fun getCommentsOfPostId(
        @Path("postId") postId: String,
        @Header("Authorization") Bearer: String
    ) : Response<Comments>

    @POST("comment")
    suspend fun postCommentOfPost(
        @Header("Authorization") Bearer: String,
        @Body comment: AddComment
    )

    @POST("comment")
    suspend fun changeCommentOfPost(
        @Header("Authorization") Bearer: String,
        @Body comment: ChangeComment
    )

    @DELETE("comment/{idComment}")
    suspend fun deleteCommentOfPost(
        @Path("idComment") id:String,
        @Header("Authorization") Bearer: String,
    )

}