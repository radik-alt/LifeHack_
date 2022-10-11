package com.example.lifehack.presentation.AddLifeHack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Posts.OnePost.CreatePost
import com.example.lifehack.data.entity.Tags
import com.example.lifehack.data.repository.ApiRepositoryImpl
import kotlinx.coroutines.launch

class CreatePostViewModel(

): ViewModel() {

    private val apiRepository = ApiRepositoryImpl()

    private var tagsList = MutableLiveData<ArrayList<String>>()
    private var imageList = MutableLiveData<ArrayList<String>>()
    private var title = MutableLiveData<String>()
    private var description = MutableLiveData<String>()

    private var token:String ?= null

    fun setToken(tokenData: RequestToken){
        token = tokenData.accessToken
    }

    fun createPost(post: CreatePost){
        viewModelScope.launch {
            if (token != null){
                apiRepository.createPost(post, "Bearer $token")
            }
        }
    }

    fun getImage():LiveData<ArrayList<String>> = imageList
    fun getTags():LiveData<ArrayList<String>> = tagsList

    fun addImage(imageData: String){
        imageList.value?.add(imageData)
    }

    fun addTags(tagsData: Tags){
        tagsList.value?.add(tagsData.tag)
    }

    fun deleteTags(tagsData: Tags){

    }

    fun deleteImage(imageData:String){

    }

    fun setTitle(titleData:String){
        title.value = titleData
    }

    fun setDescription(descData: String){
        description.value = descData
    }



    fun validCreatePost(title:String, description: String): Boolean {
        if (title.isNotEmpty() && description.isNotEmpty()) {
            return true
        }
        return false
    }
}