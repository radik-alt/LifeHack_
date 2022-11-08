package com.example.lifehack.presentation.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.JWTUtils
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.presentation.User.Data
import kotlinx.coroutines.launch

class SharedTokenViewModel(

) : ViewModel(){

    private val token = MutableLiveData<RequestToken>()
    private val userData = MutableLiveData<Data>()
    private var uid:String ?= null

    fun getToken():LiveData<RequestToken> = token
    fun setToken(tokenTemp: RequestToken){
        token.value = tokenTemp
        setUserId(tokenTemp)
    }

    fun setUserId(tokenTemp:RequestToken){
        val jwtUtils = JWTUtils()
        uid = jwtUtils.getUserId(tokenTemp.accessToken)
    }

    fun getUserId(): String? = uid


    private fun getUserData(){
        viewModelScope.launch {

        }
    }

    fun getUser():LiveData<Data> = userData
}