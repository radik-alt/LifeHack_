package com.example.lifehack.domain.repository

import com.example.lifehack.data.entity.Tags.GetListTags
import retrofit2.Response

interface TagsRepository {

    suspend fun getListTags(token:String):Response<GetListTags>

}