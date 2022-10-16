package com.example.lifehack.presentation.Account.SettingUser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.RefreshToken
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.repository.ApiRepositoryImpl
import kotlinx.coroutines.launch

class SettingViewModel(

) : ViewModel(){

    private val apiRepository = ApiRepositoryImpl()

    fun logout(token:RequestToken){
        viewModelScope.launch {
            val refreshToken = RefreshToken(
                refresh_token = token.refreshToken
            )
            apiRepository.sungOut(refreshToken)
        }
    }

    fun updateDataUser(){

    }

}