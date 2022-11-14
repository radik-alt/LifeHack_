package com.example.lifehack.domain.repository

import com.example.lifehack.data.api.ApiDataConnect
import com.example.lifehack.data.entity.Comments.AddComment.AddComment
import com.example.lifehack.data.entity.Comments.ChangeComment.ChangeComment
import com.example.lifehack.data.entity.Comments.Comments
import retrofit2.Response

interface CommentsRepository {

    suspend fun getCommentOfPost(postId: String, token: String): Response<Comments>

    suspend fun postCommentOfPost(token: String, comment: AddComment)

    suspend fun changeCommentOfPost(token:String, comment: ChangeComment)

    suspend fun deleteCommentOfPost(idComment:String, token:String)

}