package com.example.lifehack.presentation.Account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Auth.SingUpUser
import com.example.lifehack.data.repository.ApiRepositoryImpl
import kotlinx.coroutines.launch

class RegisterViewModel(

) : ViewModel() {

    private val repositoryApi = ApiRepositoryImpl()
    private val requestData = MutableLiveData<RequestToken>()

    private val imageUser = MutableLiveData<String>()

    fun singUp(user: SingUpUser){
        viewModelScope.launch {
            repositoryApi.singUp(user)
        }
    }

    fun getImageUser() : LiveData<String> = imageUser

    fun setImageUser(image: String){
        imageUser.value = image
    }
}
