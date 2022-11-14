package com.example.lifehack.data.repository

import com.example.lifehack.data.api.ApiDataConnect
import com.example.lifehack.data.entity.Stars.GetStars
import com.example.lifehack.data.entity.Stars.PostStars
import com.example.lifehack.data.entity.Stars.SetStar.SetStars
import com.example.lifehack.data.entity.Stars.Top100.Top100
import com.example.lifehack.domain.repository.StarsRepository
import retrofit2.Response

class StarsRepositoryImpl : StarsRepository {

    override suspend fun setStarsOfPost(token: String, stars: PostStars): Response<SetStars> {
        return ApiDataConnect.apiStars.postStarsOfPost(token, stars)
    }

    override suspend fun getStarsOfPost(id: String, token: String): Response<GetStars> {
        return ApiDataConnect.apiStars.getStarsOfPost(id, token)
    }

    override suspend fun getTop100OPostOfTag(tag: String, token:String): Response<Top100> {
        return ApiDataConnect.apiStars.getTop100PostOfTag(tag, token)
    }

}