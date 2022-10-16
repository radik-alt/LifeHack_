package com.example.lifehack.presentation.Rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.repository.ApiRepositoryImpl
import kotlinx.coroutines.launch

class RatingViewModel(

) : ViewModel(){

    private var token: String ?= null
    private val apiRepositoryImpl = ApiRepositoryImpl()

    fun setToken(tokenData: RequestToken){
        token = tokenData.accessToken
    }

    fun getPostTop100OfTag(tag:String){
        viewModelScope.launch {

        }
    }

}