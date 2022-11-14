package com.example.lifehack.domain.repository

import com.example.lifehack.data.api.ApiDataConnect
import com.example.lifehack.data.entity.Follow.RequestFollow
import com.example.lifehack.data.entity.Follow.postFollow.PostFollow
import retrofit2.Response

interface FollowRepository {

    suspend fun getFollowUsers(token:String): Response<RequestFollow>

    suspend fun postFollowUser(token: String, postFollow: PostFollow)

    suspend fun deleteFollowUser(token: String, followId:String)
}