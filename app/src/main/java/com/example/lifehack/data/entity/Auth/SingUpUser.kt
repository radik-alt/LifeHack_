package com.example.lifehack.data.entity.Auth

data class SingUpUser(
    val avatar: String,
    val email: String,
    val extension: String,
    val first_name: String,
    val last_name: String,
    val pass: String,
    val repass: String
)