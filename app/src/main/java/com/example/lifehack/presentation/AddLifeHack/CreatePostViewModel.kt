package com.example.lifehack.presentation.AddLifeHack

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Posts.OnePost.CreatePost
import com.example.lifehack.data.repository.ApiRepository
import kotlinx.coroutines.launch

class CreatePostViewModel(

): ViewModel() {

    private val apiRepository = ApiRepository()

    fun createPost(post: CreatePost, token: String){
        viewModelScope.launch {
            val request = apiRepository.createPost(post, "Bearer $token")
            if (request.isSuccessful){
                Log.d("PostCreatePost", request.body().toString())
            } else {
                Log.d("PostCreatePost", request.errorBody().toString())
            }

            Log.d("PostCreatePost", request.code().toString())
        }
    }
}