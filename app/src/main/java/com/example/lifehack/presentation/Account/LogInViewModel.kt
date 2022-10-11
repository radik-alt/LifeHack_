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

class LogInViewModel(

) : ViewModel(){

    private val apiRepository = ApiRepositoryImpl()
    private val coroutine = CoroutineScope(Dispatchers.Default)

    val requestAuthData = MutableLiveData<Auth>()

    fun authUser(user:AuthUser) {
        viewModelScope.launch {
            val requestAuth = apiRepository.auth(user)
            if (requestAuth.isSuccessful){
                val requestBody = requestAuth.body()
                requestAuthData.postValue(requestBody?.let { Auth.SuccessAuth(it) })
            } else {
                logServer(requestAuth.code())
                when (requestAuth.code()){
                    400 -> {
                        val errorRequest = Auth.ErrorAuth("Такого пользователя нет...")
                        requestAuthData.postValue(errorRequest)
                    }
                    401 -> {
                        val errorAuthorization = Auth.ErrorAuth("Ошибка аунтификации...")
                        requestAuthData.postValue(errorAuthorization)
                    }
                    404 -> {
                        val errorNotFound = Auth.ErrorAuth("Такого пользователя нет...")
                        requestAuthData.postValue(errorNotFound)
                    }
                    500 -> {
                        val errorServer = Auth.ErrorAuth("Ошибка сервере...")
                        requestAuthData.postValue(errorServer)
                    }
                }
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