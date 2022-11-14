package com.example.lifehack.data.api

import com.example.lifehack.data.entity.Tags.GetListTags
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface TagsApi {

    @GET("tag")
    suspend fun getListTags(
        @Header("Authorization") Bearer: String
    ):Response<GetListTags>

}