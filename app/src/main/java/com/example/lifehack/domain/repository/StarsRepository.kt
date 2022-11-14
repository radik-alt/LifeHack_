package com.example.lifehack.domain.repository

import com.example.lifehack.data.api.ApiDataConnect
import com.example.lifehack.data.entity.Follow.RequestFollow
import com.example.lifehack.data.entity.Follow.postFollow.PostFollow
import com.example.lifehack.data.entity.Stars.GetStars
import com.example.lifehack.data.entity.Stars.PostStars
import com.example.lifehack.data.entity.Stars.SetStar.SetStars
import com.example.lifehack.data.entity.Stars.Top100.Top100
import retrofit2.Response

interface StarsRepository {

    suspend fun setStarsOfPost(token: String, stars: PostStars):Response<SetStars>

    suspend fun getStarsOfPost(id: String, token: String):Response<GetStars>

    suspend fun getTop100OPostOfTag(tag: String, token:String):Response<Top100>

}