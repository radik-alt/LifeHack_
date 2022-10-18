package com.example.lifehack.presentation.Rating.viewRatingPost

import androidx.lifecycle.ViewModel
import com.example.lifehack.data.entity.Stars.Top100.Top100Item

class ViewRationViewModel(

) : ViewModel(){

    private var post: Top100Item?=null

    fun getPost():Top100Item? = post

    fun setPost(post: Top100Item){
        this.post = post
    }


}