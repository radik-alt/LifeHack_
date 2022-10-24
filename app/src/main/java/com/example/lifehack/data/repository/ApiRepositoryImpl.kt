package com.example.lifehack.data.repository

import com.example.lifehack.data.api.ApiDataConnect
import com.example.lifehack.data.entity.Auth.AuthUser
import com.example.lifehack.data.entity.Auth.RefreshToken
import com.example.lifehack.data.entity.Auth.SingUpUser
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Comments.AddComment.AddComment
import com.example.lifehack.data.entity.Comments.ChangeComment.ChangeComment
import com.example.lifehack.data.entity.Comments.Comments
import com.example.lifehack.data.entity.Follow.RequestFollow
import com.example.lifehack.data.entity.Follow.postFollow.PostFollow
import com.example.lifehack.data.entity.Posts.MainPost
import com.example.lifehack.data.entity.Posts.OnePost.CreatePost
import com.example.lifehack.data.entity.Stars.GetStars
import com.example.lifehack.data.entity.Stars.PostStars
import com.example.lifehack.data.entity.Stars.Top100.Top100
import retrofit2.Response

class ApiRepositoryImpl {

    suspend fun singUp(singUpUser: SingUpUser) : Response<RequestToken>{
        return ApiDataConnect.api.singUp(singUpUser)
    }

    suspend fun auth(user:AuthUser) : Response<RequestToken> {
        return ApiDataConnect.api.auth(user)
    }

    suspend fun sungOut(token: RefreshToken){
        ApiDataConnect.api.logout(token)
    }

    suspend fun getMainPost(bearer: String):Response<MainPost>{
        return ApiDataConnect.API_POSTS.getMainPost(bearer)
    }

    suspend fun createPost(post: CreatePost, bearer: String){
        ApiDataConnect.API_POSTS.createPost(bearer, post)
    }

    suspend fun getCommentOfPost(postId: String, token: String):Response<Comments>{
        return ApiDataConnect.apiComments.getCommentsOfPostId(postId, token)
    }

    suspend fun postCommentOfPost(token: String, comment:AddComment){
        ApiDataConnect.apiComments.postCommentOfPost(token, comment)
    }

    suspend fun changeCommentOfPost(token:String, comment:ChangeComment){
        ApiDataConnect.apiComments.changeCommentOfPost(token, comment)
    }

    suspend fun deleteCommentOfPost(idComment:String, token:String){
        ApiDataConnect.apiComments.deleteCommentOfPost(idComment, token)
    }

    suspend fun getFollowUsers(token:String):Response<RequestFollow>{
        return ApiDataConnect.apiFollow.getFollowUsers(token)
    }

    suspend fun postFollowUser(token: String, postFollow: PostFollow){
        return ApiDataConnect.apiFollow.postFollowUser(token, postFollow)
    }

    suspend fun deleteFollowUser(token: String, followId:String){
        ApiDataConnect.apiFollow.deleteFollowUser(token, followId)
    }

    suspend fun setStarsOfPost(token: String, stars: PostStars){
        ApiDataConnect.apiStars.postStarsOfPost(token, stars)
    }

    suspend fun getStarsOfPost(id: String, token: String):Response<GetStars>{
        return ApiDataConnect.apiStars.getStarsOfPost(id, token)
    }

    suspend fun getTop100OPostOfTag(tag: String, token:String):Response<Top100>{
        return ApiDataConnect.apiStars.getTop100PostOfTag(tag, token)
    }

}