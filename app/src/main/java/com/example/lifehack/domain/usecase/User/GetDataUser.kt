package com.example.lifehack.domain.usecase.User

import com.example.lifehack.data.entity.User.DataUser
import com.example.lifehack.domain.repository.UserRepository
import retrofit2.Response

class GetDataUser(private val userRepository: UserRepository) {

    suspend fun getDataUser(userId: String, token: String): Response<DataUser>{
        return userRepository.getDataUser(userId, token)
    }

}