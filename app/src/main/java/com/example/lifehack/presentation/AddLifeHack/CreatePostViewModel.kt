package com.example.lifehack.presentation.AddLifeHack

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Posts.OnePost.CreatePost
import com.example.lifehack.data.repository.ApiRepository
import kotlinx.coroutines.launch

class CreatePostViewModel(

): ViewModel() {

    private val apiRepository = ApiRepository()

    private var tags = MutableLiveData<ArrayList<String>>()
    private var image = MutableLiveData<ArrayList<String>>()
    private var title = MutableLiveData<String>()
    private var description = MutableLiveData<String>()

    fun createPost(post: CreatePost, token: String){
        viewModelScope.launch {
            apiRepository.createPost(post, "Bearer $token")
        }
    }

    fun getImage():LiveData<ArrayList<String>> = image
    fun getTags():LiveData<ArrayList<String>> = tags

    fun addImage(imageData: String){
        image.value?.add(imageData)
    }

    fun addTags(tagsData: String){
        tags.value?.add(tagsData)
    }

    fun deleteTags(tagsData: String){

    }

    fun deleteImage(imageData:String){

    }

    fun setTitle(titleData:String){
        title.value = titleData
    }

    fun setDescription(descData: String){
        description.value = descData
    }
}