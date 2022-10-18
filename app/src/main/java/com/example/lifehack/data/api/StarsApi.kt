package com.example.lifehack.data.api

import com.example.lifehack.data.entity.Stars.GetStars
import com.example.lifehack.data.entity.Stars.PostStars
import com.example.lifehack.data.entity.Stars.Top100.Top100
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface StarsApi {

    @GET("star/{id}")
    suspend fun getStarsOfPost(
        @Path("id") idPost: String,
        @Header("Authorization") Bearer: String,
    ) : Response<GetStars>


    @POST("star")
    suspend fun postStarsOfPost(
        @Header("Authorization") Bearer: String,
        @Body postStars: PostStars
    )

    @DELETE("star/{id}")
    suspend fun deleteStarsOfPost(
        @Path("id") idPost: String,
        @Header("Authorization") Bearer: String,
    )

    @GET("star/top100/{tag}")
    suspend fun getTop100PostOfTag(
        @Path("tag") tag: String,
        @Header("Authorization") Bearer: String
    ): Response<Top100>

}