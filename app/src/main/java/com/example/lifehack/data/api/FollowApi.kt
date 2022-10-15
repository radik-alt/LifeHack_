package com.example.lifehack.data.api

import com.example.lifehack.data.entity.Follow.RequestFollow
import com.example.lifehack.data.entity.Follow.postFollow.PostFollow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FollowApi {

    @GET("follow")
    suspend fun getFollowUsers(
        @Header("Authorization") Bearer: String
    ) : Response<RequestFollow>

    @POST("follow")
    suspend fun postFollowUser(
        @Header("Authorization") Bearer: String,
        @Body postFollow: PostFollow
    )

    @DELETE("follow/{followId}")
    suspend fun deleteFollowUser(
        @Header("Authorization") Bearer: String,
        @Path("followId") followId: String
    )

}