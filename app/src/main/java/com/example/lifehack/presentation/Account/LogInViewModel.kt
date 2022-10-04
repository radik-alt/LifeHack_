package com.example.lifehack.presentation.Account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.AuthUser
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.repository.ApiRepository
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogInViewModel(

) : ViewModel(){

    private val apiRepository = ApiRepository()
    private val coroutine = CoroutineScope(Dispatchers.Default)

    val requestToken = MutableLiveData<RequestToken>()

    fun authUser(user: AuthUser){
        coroutine.launch {
            authCoroutine(user)
        }
    }

    private suspend fun authCoroutine(user:AuthUser) {
        val request = apiRepository.auth(user)
        requestToken.postValue(request.body())
        return withContext(Dispatchers.Default){
            if (request.isSuccessful){
                request.body()?.let { Auth.SuccessAuth(it) }
            } else {
                Log.d("RequestCodeAuth", request.code().toString())
                Auth.ErrorAuth("Ошибка авторизации...")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutine.cancel()
    }
}

sealed class Auth(){

    class SuccessAuth(val requestToken: RequestToken):Auth()
    class ErrorAuth(val errorMessage: String):Auth()
}