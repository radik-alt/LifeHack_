package com.example.lifehack.data.entity.Follow

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val followedId: String,
    val followedName: String,
    val id: String
):Parcelable