package com.example.lifehack.presentation.Rating

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Stars.Top100.Top100
import com.example.lifehack.data.repository.ApiRepositoryImpl
import kotlinx.coroutines.launch

class RatingViewModel(

) : ViewModel(){

    private var token: String ?= null
    private var tag: String = "foto"
    private val apiRepositoryImpl = ApiRepositoryImpl()

    private val top100Post = MutableLiveData<Top100>()

    fun getTop100Post():LiveData<Top100> = top100Post

    fun setTag(tagData: String){
        Log.d("SelectTag", tagData)
        tag = tagData
    }

    fun setKindSort(){

    }

    fun setToken(tokenData: RequestToken){
        token = tokenData.accessToken
    }

    fun setTop100Post(){
        viewModelScope.launch {
            val requestGetTop100 = token?.let {
                apiRepositoryImpl.getTop100OPostOfTag(tag, it)
            }
            when (requestGetTop100?.code()) {
                200 -> {
                    top100Post.postValue(requestGetTop100.body())
                }
                else -> {
                    log("${requestGetTop100?.code()} ${requestGetTop100?.errorBody()}")
                }
            }
        }
    }

    private fun log(message:String){
        Log.d("GetTop100Request", message)
    }

    private fun getTags(){

    }


}