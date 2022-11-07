package com.example.lifehack.presentation.AddLifeHack

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Posts.OnePost.CreatePost
import com.example.lifehack.data.entity.Tags
import com.example.lifehack.data.repository.ApiRepositoryImpl
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class CreatePostViewModel(

): ViewModel() {

    private val apiRepository = ApiRepositoryImpl()

    private val allTagList = ArrayList<Tags>()
    private var tagsList = ArrayList<Tags>()
    private var imageList = ArrayList<String>()
    private var title = MutableLiveData<String>()
    private var description = MutableLiveData<String>()

    private var token:String ?= null

    init {
        allTagList.add(Tags("Дома", false))
        allTagList.add(Tags("Еда", false))
        allTagList.add(Tags("Спорт", false))
        allTagList.add(Tags("Дети", false))
        allTagList.add(Tags("Техника", false))
    }

    fun getListAllTags():ArrayList<Tags> = allTagList

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

    fun addTags(tagsData: Tags){
        tagsList.add(tagsData)
        updateTagAll(tagsData)
        Log.d("GetListTags", "$tagsList $allTagList")
    }

    private fun updateTagAll(tag:Tags){
        val index = allTagList.indexOf(tag)
        allTagList[index].select = true
    }

    fun deleteTags(tagsData: Tags){
        tagsList.remove(tagsData)
        updateTagAll(tagsData)
        Log.d("GetListTags", "$tagsList $allTagList")
    }

    fun deleteImage(imageData:String){
        imageList.remove(imageData)
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

    fun snackBar(view: View, error: String){
        Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show()
    }
}