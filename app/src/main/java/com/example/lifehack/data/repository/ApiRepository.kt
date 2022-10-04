package com.example.lifehack.data.repository

import com.example.lifehack.data.api.ApiDataConnect
import com.example.lifehack.data.entity.Auth.AuthUser
import com.example.lifehack.data.entity.Auth.SingUpUser
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Comments.Comments
import com.example.lifehack.data.entity.Posts.MainPost
import com.example.lifehack.data.entity.Posts.OnePost.CreatePost
import com.example.lifehack.data.entity.Posts.OnePost.RequestCreatePost.RequestCreatePost
import retrofit2.Response

class ApiRepository {

    suspend fun singUp(singUpUser: SingUpUser) : Response<RequestToken>{
        return ApiDataConnect.api.singUp(singUpUser)
    }

    suspend fun auth(user:AuthUser) : Response<RequestToken> {
        return ApiDataConnect.api.auth(user)
    }

    suspend fun getMainPost(bearer: String):Response<MainPost>{
        return ApiDataConnect.API_POSTS.getMainPost(bearer)
    }

    suspend fun createPost(post: CreatePost, bearer: String){
        ApiDataConnect.API_POSTS.createPost(bearer, post)
    }

    suspend fun getCommentOfPost(postId: String, token: String):Response<Comments>{
        return ApiDataConnect.API_POSTS.getCommentsOfPostId(postId, token)
    }

}