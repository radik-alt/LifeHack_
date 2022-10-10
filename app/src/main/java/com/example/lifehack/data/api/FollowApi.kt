package com.example.lifehack.data.api

import com.example.lifehack.data.entity.Follow.RequestFollow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface FollowApi {


    @GET("follow")
    suspend fun getFollowUsers(
        @Header("Authorization") Bearer: String
    ) : Response<RequestFollow>

}