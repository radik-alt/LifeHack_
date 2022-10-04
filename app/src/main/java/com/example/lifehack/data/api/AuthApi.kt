package com.example.lifehack.data.api

import com.example.lifehack.data.Utils
import com.example.lifehack.data.Utils.AUTH_URl
import com.example.lifehack.data.Utils.SIGNUP_URl
import com.example.lifehack.data.entity.Auth.AuthUser
import com.example.lifehack.data.entity.Auth.RefreshToken
import com.example.lifehack.data.entity.Auth.SingUpUser
import com.example.lifehack.data.entity.Auth.RequestToken
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {


    @POST(SIGNUP_URl)
    suspend fun singUp(
        @Body user: SingUpUser
    ) : Response<RequestToken>


    @Headers("Content-Type: application/json")
    @POST("auth/signin")
    suspend fun auth(
        @Body user: AuthUser
    ) : Response<RequestToken>



    @POST("auth/signout")
    suspend fun logout(
        @Body token: RefreshToken
    )



}