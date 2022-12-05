package com.example.lifehack.presentation.Search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Recommendation.RecommendationPosts
import com.example.lifehack.data.repository.PostRepositoryImpl
import com.example.lifehack.domain.usecase.Post.Recommendation.GetRecommendationPosts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SearchViewModel(

) : ViewModel(){

    private val postRepositoryImpl = PostRepositoryImpl()
    private val getRecommendationPosts = GetRecommendationPosts(postRepositoryImpl)

    private val coroutine = CoroutineScope(Dispatchers.Default)
    private val recommendationPosts = MutableLiveData<RecommendationPosts>()
    private var token:RequestToken ?= null

    fun setToken(token_: RequestToken){
        token = token_
    }

    fun getRecommendationPosts() = recommendationPosts

    fun getApiRecommendationPosts(){
        coroutine.launch{
            token?.let {
                val requestApi = getRecommendationPosts.getRecommendationPosts(it.accessToken)
                when (requestApi.code()){
                    200 -> {
                        recommendationPosts.postValue(requestApi.body())
                    }
                }
                Log.d("RequestApiRec", requestApi.code().toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutine.cancel()
    }
}