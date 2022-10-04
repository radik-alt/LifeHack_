package com.example.lifehack.data.api

import com.example.lifehack.data.Utils.AUTH_SIGNOUT
import com.example.lifehack.data.Utils.AUTH_URl
import com.example.lifehack.data.Utils.SIGNUP_URl
import com.example.lifehack.data.entity.Auth.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {


    @POST(SIGNUP_URl)
    suspend fun singUp(
        @Body user: SingUpUser
    ) : Response<RequestToken>


    @Headers("Content-Type: application/json")
    @POST(AUTH_URl)
    suspend fun auth(
        @Body user: AuthUser
    ) : Response<RequestToken>


    @GET()
    suspend fun getImageUser(

    ): Response<ImageUser>


    @POST(AUTH_SIGNOUT)
    suspend fun logout(
        @Body token: RefreshToken
    )



}