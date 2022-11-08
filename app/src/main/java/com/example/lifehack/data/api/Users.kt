package com.example.lifehack.data.api

import com.example.lifehack.presentation.User.DataUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface Users {

    @GET("user/{userId}")
    suspend fun getDataUser(
        @Path("userId") userId:String,
        @Header("Authorization") Bearer: String,
    ) : Response<DataUser>

}