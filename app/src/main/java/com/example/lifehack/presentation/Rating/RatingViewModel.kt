package com.example.lifehack.presentation.Rating

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Stars.Top100.Top100
import com.example.lifehack.data.entity.Tags.GetListTagsItem
import com.example.lifehack.data.repository.ApiRepositoryImpl
import com.example.lifehack.data.repository.TagsRepositoryImpl
import kotlinx.coroutines.launch

class RatingViewModel(

) : ViewModel(){

    private var token: String ?= null
    private var tag: String = "plants"

    private val apiRepositoryImpl = ApiRepositoryImpl()
    private val tagsRepositoryImpl = TagsRepositoryImpl()

    private val listTag = MutableLiveData<ArrayList<String>>()
    private val top100Post = MutableLiveData<Top100>()

    fun getListTag():LiveData<ArrayList<String>> = listTag
    fun getTop100Post():LiveData<Top100> = top100Post

    fun setTag(tag: String){
        Log.d("SelectTag", tag)
        this.tag = tag
    }

    fun setKindSort(){

    }

    fun setToken(tokenData: RequestToken){
        token = tokenData.accessToken
    }

    fun setTop100Post(){
        viewModelScope.launch {
            val requestGetTop100 = token?.let {
                apiRepositoryImpl.getTop100OPostOfTag(tag, it)
            }
            when (requestGetTop100?.code()) {
                200 -> {
                    top100Post.postValue(requestGetTop100.body())
                }
                else -> {
                    log("${requestGetTop100?.code()} ${requestGetTop100?.errorBody()}")
                }
            }
        }
    }

    fun getListTagsRequest(){
        viewModelScope.launch {
            val requestTags = token?.let { tagsRepositoryImpl.getListTags(it) }
            when (requestTags?.code()){
                200 -> {
                    val allTags = requestTags.body()
                    val listTagsString = ArrayList<String>()
                    if (allTags != null) {
                        for (i in allTags){
                            listTagsString.add(i.title)
                        }
                    }
                    listTag.postValue(listTagsString)
                } else -> {
                Log.d("GetListTags", "${requestTags?.code()}")
            }
            }
        }
    }

    private fun log(message:String){
        Log.d("GetTop100Request", message)
    }

    private fun getTags(){

    }


}