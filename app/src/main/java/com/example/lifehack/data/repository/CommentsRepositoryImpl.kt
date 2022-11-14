package com.example.lifehack.data.repository

import com.example.lifehack.data.api.ApiDataConnect
import com.example.lifehack.data.entity.Comments.AddComment.AddComment
import com.example.lifehack.data.entity.Comments.ChangeComment.ChangeComment
import com.example.lifehack.data.entity.Comments.Comments
import com.example.lifehack.domain.repository.CommentsRepository
import retrofit2.Response

class CommentsRepositoryImpl : CommentsRepository {

    override suspend fun getCommentOfPost(postId: String, token: String):Response<Comments>{
        return ApiDataConnect.apiComments.getCommentsOfPostId(postId, token)
    }

    override suspend fun postCommentOfPost(token: String, comment:AddComment){
        ApiDataConnect.apiComments.postCommentOfPost(token, comment)
    }

    override suspend fun changeCommentOfPost(token:String, comment:ChangeComment){
        ApiDataConnect.apiComments.changeCommentOfPost(token, comment)
    }

    override suspend fun deleteCommentOfPost(idComment:String, token:String){
        ApiDataConnect.apiComments.deleteCommentOfPost(idComment, token)
    }
}