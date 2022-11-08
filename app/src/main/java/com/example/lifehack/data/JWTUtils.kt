package com.example.lifehack.data

import com.auth0.android.jwt.JWT

class JWTUtils() {

    fun getUserId(JWTEncoded: String): String {
        val jwt = JWT(JWTEncoded)
        return jwt.getClaim("userId").asString().toString()
    }

}