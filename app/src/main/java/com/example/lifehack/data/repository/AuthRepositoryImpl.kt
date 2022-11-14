package com.example.lifehack.data.repository

import com.example.lifehack.data.api.ApiDataConnect
import com.example.lifehack.data.entity.Auth.AuthUser
import com.example.lifehack.data.entity.Auth.RefreshToken
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Auth.SingUpUser
import com.example.lifehack.domain.repository.AuthRepository
import retrofit2.Response

class AuthRepositoryImpl:AuthRepository {

    override suspend fun singUp(singUpUser: SingUpUser): Response<RequestToken> {
        return ApiDataConnect.api.singUp(singUpUser)
    }

    override suspend fun auth(user: AuthUser): Response<RequestToken> {
        return ApiDataConnect.api.auth(user)
    }

    override suspend fun sungOut(token: RefreshToken) {
        ApiDataConnect.api.logout(token)
    }
}