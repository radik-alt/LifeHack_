package com.example.lifehack.presentation.Home.editPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Posts.UpdatePost.UpdatePost
import com.example.lifehack.data.repository.PostRepositoryImpl
import kotlinx.coroutines.launch

class EditPostViewModel(

) : ViewModel(){

    private val postRepositoryImpl = PostRepositoryImpl()
    private var token:String?=null

    fun setToken(token: RequestToken){
        this.token = token.accessToken
    }

    fun updatePost(updatePost: UpdatePost){
        viewModelScope.launch {
            token?.let {
                postRepositoryImpl.updatePost(updatePost, it)
            }
        }
    }

}