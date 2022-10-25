package com.example.lifehack.data

import android.util.Base64
import android.util.Log
import java.io.UnsupportedEncodingException

class JWTUtils() {

    fun decode(JWTEncoded:String) {
        try {
            val split = JWTEncoded.split("\\.");
            val header = getJson(split[0])
            val header2 = header.split(",")
            Log.d("JWT_DECODED", "Header: $header2");
        } catch (e: UnsupportedEncodingException) {
            Log.d("JWT_DECODED", "Header: " + e.message);
        }
    }

    private fun getJson(strEncoded:String): String {
        val decodedBytes:ByteArray = Base64.decode(strEncoded, Base64.URL_SAFE);
        return String(decodedBytes);
    }

}