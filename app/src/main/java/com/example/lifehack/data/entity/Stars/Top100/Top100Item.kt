package com.example.lifehack.data.entity.Stars.Top100

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Top100Item(
    val count_star: Int,
    val description: String,
    val post_id: String,
    val title: String
):Parcelable