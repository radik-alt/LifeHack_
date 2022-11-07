package com.example.lifehack.presentation.Home.viewPost

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehack.data.entity.Comments.AddComment.AddComment
import com.example.lifehack.data.entity.Comments.ChangeComment.ChangeComment
import com.example.lifehack.data.entity.Comments.Comments
import com.example.lifehack.data.entity.Comments.Data
import com.example.lifehack.data.entity.Follow.postFollow.PostFollow
import com.example.lifehack.data.entity.Stars.GetStars
import com.example.lifehack.data.entity.Stars.PostStars
import com.example.lifehack.data.repository.ApiRepositoryImpl
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ViewPostViewModel(

) : ViewModel(){

    private val apiRepository = ApiRepositoryImpl()
    private val comments = MutableLiveData<Comments>()
    private val isEditComment = MutableLiveData<Boolean>(false)
    val selectCommentChange = MutableLiveData<Data>()
    val starsOfPost = MutableLiveData<GetStars>()

    private var token:String?=null
    private var idPost: String?=null

    fun setPostId(idPost:String){
        this.idPost = idPost
    }

    fun setToken(tokenTemp:String){
        token = tokenTemp
    }

    fun getComments(postId: String){
        viewModelScope.launch {
            val requestComments = apiRepository.getCommentOfPost(postId, "Bearer $token")
            if (requestComments.isSuccessful){
                comments.postValue(requestComments.body())
                Log.d("RequestComments", requestComments.body().toString())
            } else {
                Log.d("RequestComments", requestComments.errorBody().toString())
            }
            Log.d("RequestComments", requestComments.code().toString())
        }
    }

    fun deletePost(postId: String){
        viewModelScope.launch {
            token?.let {
                apiRepository.deletePost(postId, it)
            }
        }
    }

    fun getIsEditComment(): LiveData<Boolean> = isEditComment

    fun setIsEditComment(isEdit: Boolean){
        isEditComment.value = isEdit
    }


    fun getCommentsData():LiveData<Comments> = comments

    fun addComment(addComment: AddComment){
        viewModelScope.launch {
            if (token != null){
                apiRepository.postCommentOfPost("Bearer $token", addComment)
            }
        }
    }

    fun validCommentChange(changeComment: String, comment: Data): Boolean{
        return changeComment.trim() == comment.comment
    }

    fun changeComment(changeComment: ChangeComment){
       viewModelScope.launch {
           token?.let {
               apiRepository.changeCommentOfPost(it, changeComment)
           }
       }
    }

    fun deleteComment(idComment:String){
        viewModelScope.launch {
            token?.let {
                apiRepository.deleteCommentOfPost(idComment, it)
            }
        }
    }

    fun showSnackBar(view: View, error: String){
        Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show()
    }

    fun subscribe(postFollow: PostFollow){
        viewModelScope.launch {
            token?.let {
                apiRepository.postFollowUser(it, postFollow)
            }
        }
    }

//    fun setUpdateStarsOfPost(stars: PostStars){
//        viewModelScope.launch {
//            token?.let {
//                apiRepository.setStarsOfPost(it, stars)
//            }
//        }
//    }
//
//    fun getStarsOfPost(){
//        viewModelScope.launch {
//            val getStars = token?.let {
//                idPost?.let { idPost -> apiRepository.getStarsOfPost(idPost, it) }
//            }
//            if (getStars != null){
//                if (getStars.isSuccessful){
//                    starsOfPost.postValue(getStars.body())
//                    Log.d("GetStarsData", getStars.body().toString())
//                } else {
//                    Log.d("GetRequestStars", getStars.code().toString())
//                }
//            }
//        }
//    }

    fun validFollowerUser(userid:String):Flow<Boolean> = flow{
        viewModelScope.launch {
            val allFollowerUser = token?.let { apiRepository.getFollowUsers(it) }
            if (allFollowerUser?.isSuccessful == true){
                val listUser = allFollowerUser.body()?.data
                if (listUser != null) {
                    for (i in listUser){
                        if (i.followedId == userid){
                            emit(true)
                        }
                    }
                }
            }
        }
    }

    fun validCommentSend(comment:String):Boolean{
        return comment.isNotEmpty()
    }

}

sealed class Stars{
    class SuccessStars(val stars: GetStars):Stars()
    class ErrorStar(val error: String):Stars()
}

sealed class CommentsSealed{
    class Success(val comment: Comments):CommentsSealed()
    class Error(val message: String):CommentsSealed()
}