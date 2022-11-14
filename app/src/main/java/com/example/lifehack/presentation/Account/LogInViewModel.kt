package com.example.lifehack.presentation.Account

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.AuthUser
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.repository.ApiRepositoryImpl
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class LogInViewModel(

) : ViewModel(){

    private val apiRepository = ApiRepositoryImpl()
    private val coroutine = CoroutineScope(Dispatchers.Default)

    val requestAuthData = MutableLiveData<Auth>()
    private var auth:Auth?=null


    fun authUser(user:AuthUser) {
        viewModelScope.launch {
            val requestAuth = apiRepository.auth(user)
            if (requestAuth.isSuccessful){
                val requestBody = requestAuth.body()
                auth = requestBody?.let { Auth.SuccessAuth(it) }
                requestAuthData.value = requestBody?.let { Auth.SuccessAuth(it) }
            } else {
                logServer(requestAuth.code())
                val error = getErrorRequestCode(requestAuth.code())
                requestAuthData.value = error
            }
        }
    }

    private fun getErrorRequestCode(code:Int): Auth.ErrorAuth {
        return when (code){
            400 -> {
                Auth.ErrorAuth("Такого пользователя нет...")
            }
            401 -> {
                Auth.ErrorAuth("Ошибка аунтификации...")
            }
            404 -> {
                Auth.ErrorAuth("Такого пользователя нет...")
            }
            500 -> {
                Auth.ErrorAuth("Ошибка сервере...")
            }
            else -> {
                Auth.ErrorAuth("Неизвестна ошибка...")
            }
        }
    }

    private fun logServer(code:Int){
        Log.d("GetLogRequestApi", code.toString())
    }

    fun showSnackBar(view: View, error: String){
        Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show()
    }

    fun valid(login:String, password: String):Boolean{
        return login.isNotEmpty() && password.isNotEmpty()
    }

    override fun onCleared() {
        super.onCleared()
        coroutine.cancel()
    }
}

sealed class Auth{
    class SuccessAuth(val requestToken: RequestToken):Auth()
    class ErrorAuth(val errorMessage: String):Auth()
}