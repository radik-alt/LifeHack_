package com.example.lifehack.data.repository

import com.example.lifehack.data.api.ApiDataConnect
import com.example.lifehack.data.entity.User.DataUser
import com.example.lifehack.domain.repository.UserRepository
import retrofit2.Response

class UserRepositoryImpl: UserRepository {

    override suspend fun getDataUser(userId: String, token: String): Response<DataUser> {
        return ApiDataConnect.apiUser.getDataUser(userId, token)
    }
}