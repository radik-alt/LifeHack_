package com.example.lifehack.presentation.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.User.Data
import kotlinx.coroutines.launch

class SharedTokenViewModel(

) : ViewModel(){

    private val token = MutableLiveData<RequestToken>()
    private val userData = MutableLiveData<Data>()

    fun getToken():LiveData<RequestToken> = token
    fun setToken(tokenTemp: RequestToken){
        token.value = tokenTemp
    }

    private fun getUserData(){
        viewModelScope.launch {

        }
    }

    fun getUser():LiveData<Data> = userData
}