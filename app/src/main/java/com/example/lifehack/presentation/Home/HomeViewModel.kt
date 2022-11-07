package com.example.lifehack.presentation.Home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Posts.MainPost
import com.example.lifehack.data.repository.ApiRepositoryImpl
import kotlinx.coroutines.launch

class HomeViewModel(

) : ViewModel(){

    private val apiRepository = ApiRepositoryImpl()
    private val postMain = MutableLiveData<MainPost>()
    private val scrollPost = MutableLiveData<Int>()

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

    fun getScrollPost() :LiveData<Int> = scrollPost

    fun getPostsData():LiveData<MainPost> = postMain
}