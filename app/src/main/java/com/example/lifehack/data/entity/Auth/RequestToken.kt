package com.example.lifehack.data.entity.Auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class RequestToken(
    val accessToken: String,
    val refreshToken: String,
    val expires: Int,
)