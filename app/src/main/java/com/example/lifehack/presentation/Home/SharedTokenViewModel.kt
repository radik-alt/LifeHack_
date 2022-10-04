package com.example.lifehack.presentation.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lifehack.data.entity.Auth.RequestToken

class SharedTokenViewModel(

) : ViewModel(){

    private val token = MutableLiveData<RequestToken>()

    fun getToken():LiveData<RequestToken> = token

    fun setToken(tokenTemp: RequestToken){
        token.value = tokenTemp
    }
}