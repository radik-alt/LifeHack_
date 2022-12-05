package com.example.lifehack.presentation.AddLifeHack

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Posts.OnePost.CreatePost
import com.example.lifehack.data.entity.Tags.GetListTagsItem
import com.example.lifehack.data.entity.TagsDTO
import com.example.lifehack.data.repository.ApiRepositoryImpl
import com.example.lifehack.data.repository.TagsRepositoryImpl
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class CreatePostViewModel(

): ViewModel() {

    private val apiRepository = ApiRepositoryImpl()
    private val tagsRepositoryImpl = TagsRepositoryImpl()

    val listTag = MutableLiveData<ArrayList<GetListTagsItem>>()

    private val allTagList = ArrayList<TagsDTO>()
    private var tagsList = ArrayList<TagsDTO>()
    private var imageList = ArrayList<String>()
    private var title = MutableLiveData<String>()
    private var description = MutableLiveData<String>()

    private var token:String ?= null

    fun getListAllTags():ArrayList<TagsDTO> = allTagList

    fun setToken(tokenData: RequestToken){
        token = tokenData.accessToken
    }

    fun createPost(post: CreatePost){
        viewModelScope.launch {
            Log.d("CreatePostView", post.toString())
            if (token != null){
                apiRepository.createPost(post, "Bearer $token")
            }
        }
    }

    fun getImage():ArrayList<String> = imageList
    fun getTags():ArrayList<String> {
        val list = ArrayList<String>()
        for (i in tagsList){
            list.add(i.tag)
        }
        return list
    }

    fun addImage(imageData: String){
        imageList.add(imageData)
    }

    fun addTags(tagsData: TagsDTO){
        tagsList.add(tagsData)
        updateTagAll(tagsData)
        Log.d("GetListTags", "$tagsList $allTagList")
    }

    private fun updateTagAll(tag:TagsDTO){
        val index = allTagList.indexOf(tag)
        allTagList[index].select = true
    }

    fun deleteTags(tagsData: TagsDTO){
        tagsList.remove(tagsData)
        updateTagAll(tagsData)
        Log.d("GetListTags", "$tagsList $allTagList")
    }

    fun deleteImage(imageData:String){
        imageList.remove(imageData)
    }

    fun getListTags(){
        viewModelScope.launch {
            val requestTags = token?.let { tagsRepositoryImpl.getListTags(it) }
            when (requestTags?.code()){
                200 -> {
                    listTag.postValue(requestTags.body())
                } else -> {
                    Log.d("GetListTags", "${requestTags?.code()}")
                }
            }
        }
    }

    fun validCreatePost(title:String, description: String): Boolean {
        if (title.isNotEmpty() && description.isNotEmpty()) {
            return true
        }
        return false
    }

    fun snackBar(view: View, error: String){
        Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show()
    }
}