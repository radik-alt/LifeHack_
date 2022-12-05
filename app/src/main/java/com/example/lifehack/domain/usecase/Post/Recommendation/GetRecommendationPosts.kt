package com.example.lifehack.domain.usecase.Post.Recommendation

import com.example.lifehack.data.entity.Recommendation.RecommendationPosts
import com.example.lifehack.domain.repository.PostRepository
import retrofit2.Response

class GetRecommendationPosts (private val postRepository: PostRepository) {

    suspend fun getRecommendationPosts(bearer:String):Response<RecommendationPosts>{
        return postRepository.getRecommendationPosts(bearer)
    }

}