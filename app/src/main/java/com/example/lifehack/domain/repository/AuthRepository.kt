package com.example.lifehack.domain.repository

import com.example.lifehack.data.entity.Auth.AuthUser
import com.example.lifehack.data.entity.Auth.RefreshToken
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Auth.SingUpUser
import retrofit2.Response

interface AuthRepository {

    suspend fun singUp(singUpUser: SingUpUser) : Response<RequestToken>

    suspend fun auth(user: AuthUser) : Response<RequestToken>

    suspend fun sungOut(token: RefreshToken)

}