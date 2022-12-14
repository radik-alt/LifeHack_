package com.example.lifehack.data.entity.Posts

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Content(
    val count_star: Int,
    val description: String,
    val tags: List<String>?,
    val post_id: String,
    val user_id:String,
    val title: String
): Parcelable