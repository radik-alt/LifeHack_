package com.example.lifehack.data.repository

import com.example.lifehack.data.api.ApiDataConnect
import com.example.lifehack.data.entity.Follow.RequestFollow
import com.example.lifehack.data.entity.Follow.postFollow.PostFollow
import com.example.lifehack.domain.repository.FollowRepository
import retrofit2.Response

class FollowRepositoryImpl: FollowRepository {

    override suspend fun getFollowUsers(token:String): Response<RequestFollow> {
        return ApiDataConnect.apiFollow.getFollowUsers(token)
    }

    override suspend fun postFollowUser(token: String, postFollow: PostFollow){
        return ApiDataConnect.apiFollow.postFollowUser(token, postFollow)
    }

    override suspend fun deleteFollowUser(token: String, followId:String){
        ApiDataConnect.apiFollow.deleteFollowUser(token, followId)
    }

}