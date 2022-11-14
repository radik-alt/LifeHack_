package com.example.lifehack.data.repository

import com.example.lifehack.data.api.ApiDataConnect
import com.example.lifehack.data.entity.Tags.GetListTags
import com.example.lifehack.domain.repository.TagsRepository
import retrofit2.Response

class TagsRepositoryImpl: TagsRepository {

    override suspend fun getListTags(token: String): Response<GetListTags> {
        return ApiDataConnect.apiTags.getListTags(token)
    }
}