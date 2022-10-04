package com.example.lifehack.presentation.Home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Posts.Content
import com.example.lifehack.data.entity.Posts.MainPost
import com.example.lifehack.data.repository.ApiRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(

) : ViewModel(){

    private val apiRepository = ApiRepository()
    private val postMain = MutableLiveData<MainPost>()

    fun getPosts(token:String){
        viewModelScope.launch {
            val request = apiRepository.getMainPost("Bearer $token")
            if (request.isSuccessful){
                postMain.postValue(request.body())
                Log.d("RequestMainPost", request.body().toString())
            } else {
                Log.d("RequestMainPost", request.errorBody().toString())
            }
            Log.d("RequestMainPost", request.code().toString())
        }
    }

    fun getPostsData():LiveData<MainPost> = postMain
}