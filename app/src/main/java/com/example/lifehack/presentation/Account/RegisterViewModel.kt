package com.example.lifehack.presentation.Account

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Auth.SingUpUser
import com.example.lifehack.data.repository.ApiRepositoryImpl
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RegisterViewModel(

) : ViewModel() {

    private val repositoryApi = ApiRepositoryImpl()
    val requestRegistertData = MutableLiveData<Register>()

    private val imageUser = MutableLiveData<String>()

    fun singUp(user: SingUpUser){
        viewModelScope.launch {
            val requestRegister = repositoryApi.singUp(user)
            if (requestRegister.isSuccessful){
                val requestBody = requestRegister.body()
                requestRegistertData.postValue(requestBody?.let {
                    Register.SuccessRegister(it)
                })
            } else {
                when (requestRegister.code()){
                    400 -> {
                        val errorRequest = Register.ErrorRegister("Такого пользователя нет...")
                        requestRegistertData.postValue(errorRequest)
                    }
                    401 -> {
                        val errorAuthorization = Register.ErrorRegister("Ошибка аунтификации...")
                        requestRegistertData.postValue(errorAuthorization)
                    }
                    404 -> {
                        val errorNotFound = Register.ErrorRegister("Такого пользователя нет...")
                        requestRegistertData.postValue(errorNotFound)
                    }
                    500 -> {
                        val errorServer = Register.ErrorRegister("Ошибка сервере...")
                        requestRegistertData.postValue(errorServer)
                    }
                }
            }
        }
    }

    fun valid(firstName:String, lastName: String, email:String, password:String): Boolean {
        if (firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            email.isNotBlank() &&
            password.isNotBlank()
        ) {
            return true
        }
        return false
    }

    fun showSnackBar(view:View, error: String) = Snackbar.make(view, error, Snackbar.LENGTH_LONG).show()

    fun getImageUser() : LiveData<String> = imageUser

    fun setImageUser(image: String){
        imageUser.value = image
    }
}

sealed class Register{

    class SuccessRegister(val requestToken: RequestToken): Register()
    class ErrorRegister(val error:String): Register()
}
