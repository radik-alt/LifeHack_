package com.example.lifehack.presentation.Home.viewPost

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Stars.PostStars
import com.example.lifehack.data.repository.ApiRepositoryImpl
import kotlinx.coroutines.launch

class StarsViewModel(

) :ViewModel(){

    private val apiRepositoryImpl = ApiRepositoryImpl()
    private var postId:String ?= null
    private var token:String?=null


    fun setPostId(postIdData:String){
        postId = postIdData
    }

    fun setToken(tokenData: RequestToken) {
        token = tokenData.accessToken
    }


    fun setStar(countStar:Int){
        val postStars = postId?.let { PostStars(countStar, it) }
        Log.d("GetPostStar", postStars.toString())
        viewModelScope.launch {
            token?.let {
                val requestSetStar = postStars?.let { it1 ->
                    apiRepositoryImpl.setStarsOfPost(it, it1)
                }
                if (requestSetStar?.isSuccessful == true){
                    Log.d("GetPostStar", requestSetStar.body().toString())
                }

            }
        }
    }


}