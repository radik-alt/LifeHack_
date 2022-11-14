package com.example.lifehack.domain.repository

import com.example.lifehack.data.api.ApiDataConnect
import com.example.lifehack.data.entity.User.DataUser
import retrofit2.Response

interface UserRepository {

    suspend fun getDataUser(userId: String, token: String): Response<DataUser>

//    suspend fun updateDataUser()
}