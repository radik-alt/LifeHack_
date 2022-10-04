package com.example.lifehack.presentation.Account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Auth.SingUpUser
import com.example.lifehack.data.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(

) : ViewModel() {

    private val repositoryApi = ApiRepository()
    private val requestData = MutableLiveData<RequestToken>()


    fun singUp(user: SingUpUser){
        viewModelScope.launch {
            repositoryApi.singUp(user)
        }
    }
}
