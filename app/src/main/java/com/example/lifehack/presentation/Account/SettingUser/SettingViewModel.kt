package com.example.lifehack.presentation.Account.SettingUser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.Utils
import com.example.lifehack.data.entity.Auth.RefreshToken
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Posts.ProfilePosts.PostsUserProfile
import com.example.lifehack.data.entity.User.DataUser
import com.example.lifehack.data.repository.ApiRepositoryImpl
import kotlinx.coroutines.launch

class SettingViewModel(

) : ViewModel(){

    private val apiRepository = ApiRepositoryImpl()
    private var dataUser = MutableLiveData<DataUser>()
    private var dataPosts = MutableLiveData<PostsUserProfile>()
    private var token:RequestToken?=null

    fun setToken(tokenData: RequestToken) {
        token = tokenData
    }

    fun getDataUser():LiveData<DataUser> = dataUser
    fun getDataPost():LiveData<PostsUserProfile> = dataPosts

    fun logout(){
        viewModelScope.launch {
            val refreshToken = token?.let {
                RefreshToken(
                    refresh_token = it.refreshToken
                )
            }
            if (refreshToken != null) {
                apiRepository.sungOut(refreshToken)
            }
        }
    }

    fun getPostUser(){
        viewModelScope.launch {
            val requestPostProfile = token?.let {
                apiRepository.getPostsUserProfile(
                    userId = Utils.user_default,
                    bearer = it.accessToken
                )
            }
            when (requestPostProfile?.code()){
                200 -> {
                    dataPosts.postValue(requestPostProfile.body())
                    Log.d("GetMyLifeHacks", requestPostProfile.body().toString())
                }
            }
            Log.d("GetMyLifeHacks", requestPostProfile?.code().toString())
        }
    }

    fun getDataUser(userId:String){
        viewModelScope.launch {
            val requestDataUser = token?.let {
                apiRepository.getDataUser(userId, it.accessToken)
            }
            if (requestDataUser != null) {
                dataUser.postValue(requestDataUser.body())
            }
            Log.d("GetDataUserApi", requestDataUser?.code().toString())
        }
    }

    fun updateDataUser(){

    }

}