package com.example.lifehack.presentation.Friends.ViewFirends

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Posts.GetPostsOfUser.PostsOfUser
import com.example.lifehack.data.entity.Posts.ProfilePosts.PostsUserProfile
import com.example.lifehack.data.repository.ApiRepositoryImpl
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ViewFriendsViewModel(

) : ViewModel(){

    private val apiRepositoryImpl = ApiRepositoryImpl()
    private var token:String ?=null
    private var postsOfUser = MutableLiveData<PostsUserProfile>()

    fun setToken(tokenData:RequestToken){
        token = tokenData.accessToken
    }

    fun getPostsOfUser():LiveData<PostsUserProfile> = postsOfUser

    fun getAllPostUser(userId:String){
        viewModelScope.launch {
            val requestGetPostsUser = token?.let {
                apiRepositoryImpl.getPostsUserProfile(userId, it)
            }
            when (requestGetPostsUser?.code()){
                200 -> {
                    postsOfUser.postValue(requestGetPostsUser.body())
                }
            }
        }
    }

    fun getAllTagsUser(){

    }

    fun deleteFollow(){

    }

    fun snackBar(view: View, error: String){
        Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show()
    }

}