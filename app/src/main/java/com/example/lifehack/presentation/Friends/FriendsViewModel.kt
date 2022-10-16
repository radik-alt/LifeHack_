package com.example.lifehack.presentation.Friends

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Follow.Data
import com.example.lifehack.data.repository.ApiRepositoryImpl
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class FriendsViewModel(

) : ViewModel(){

    private val apiRepository = ApiRepositoryImpl()
    private var users = MutableLiveData<FollowUsers>()
    private var filterUsers = ArrayList<Data>()

    private var token:String?=null

    fun setToken(tokenTemp: String){
        token = tokenTemp
    }

    fun getFollowUsers(){
        viewModelScope.launch {
            val requestFollow = apiRepository.getFollowUsers("Bearer $token")
            if (requestFollow.isSuccessful){
                users.postValue(requestFollow.body()?.let {
                    FollowUsers.Success(it.data)
                })
                val req = requestFollow.body()
                if (req != null) {
                    filterUsers.clear()
                    filterUsers = req.data as ArrayList<Data>
                }
                Log.d("GetFollowUsers", requestFollow.body().toString())
            } else {
                when (requestFollow.code()){
                    500 -> {
                        users.postValue(FollowUsers.Error("Сервер упал!"))
                    }
                    404 -> {
                        users.postValue(FollowUsers.Error("Тут даже фиксики не помогут!"))
                    }
                    401 -> {
                        users.postValue(FollowUsers.Error("Авторизируйтесь...!"))
                    }
                }
                Log.d("GetFollowUsers", requestFollow.errorBody().toString())
            }
        }
    }

    fun deleteFollowUser(followId:String){
        viewModelScope.launch {
            token?.let {
                apiRepository.deleteFollowUser(it, followId)
            }
        }
    }

    fun getSearchFollower(inputName:CharSequence){
        val filter = filterUsers.filter {
            it.followedName
                .lowercase()
                .trim()
                .contains(inputName.trim())
        }
        Log.d("FilterList", filter.toString())
    }

    fun getList():LiveData<FollowUsers> = users

    fun snackBar(view: View, error: String){
        Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show()
    }
}

sealed class FollowUsers(){

    class Success(val users: List<Data>):FollowUsers()
    class Error(val error: String):FollowUsers()
}