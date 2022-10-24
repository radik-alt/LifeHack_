package com.example.lifehack.presentation.Home.viewPost

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lifehack.R
import com.example.lifehack.data.entity.Comments.AddComment.AddComment
import com.example.lifehack.data.entity.Comments.ChangeComment.ChangeComment
import com.example.lifehack.data.entity.Comments.Comments
import com.example.lifehack.data.entity.Comments.Data
import com.example.lifehack.databinding.FragmentPostShowBinding
import com.example.lifehack.presentation.Home.SharedTokenViewModel
import com.example.lifehack.presentation.adapter.AdapterComments.AdapterComments
import com.example.lifehack.presentation.adapter.intreface.OnClickComment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.RuntimeException

class PostShowFragment : Fragment() {


    private var _binding: FragmentPostShowBinding?=null
    private val binding: FragmentPostShowBinding
        get() = _binding ?: throw RuntimeException("FragmentPostShowBinding == null")

    private val navPost by navArgs<PostShowFragmentArgs>()
    private val postViewModel : ViewPostViewModel by lazy {
        ViewModelProvider(this)[ViewPostViewModel::class.java]
    }

    private val sharedViewModel : SharedTokenViewModel by activityViewModels()
    private var comments: Comments?=null


    override fun onResume() {
        hideBottomView()
        super.onResume()
        sharedViewModel.getToken().observe(viewLifecycleOwner){
            postViewModel.setToken(it.accessToken)
            postViewModel.setPostId(navPost.contentOfPost.post_id)
            getComments()
            showPost()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.layoutViewPost.setOnClickListener {
            clearFocusEditText()
        }

        if (!binding.equals(binding.textComment) && !binding.equals(binding.sendComment)){
            clearFocusEditText()
        }

        binding.backHome.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.sendComment.setOnClickListener {
            if (postViewModel.getIsEditComment().value == true){
                updateComment()
            } else {
                sendComment()
            }
        }

        binding.imageView.setOnClickListener {
            clearFocusEditText()
        }

        binding.subscribe.setOnClickListener {
            subscription()
            clearFocusEditText()
        }

        binding.descriptionPost.setOnClickListener {
            binding.descriptionPost.maxLines = Int.MAX_VALUE
            clearFocusEditText()
        }


    }

    private fun hideBottomView(){
        val fragmentActivity = activity
        if (activity != null){
            val bottom = fragmentActivity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            if (bottom != null && bottom.visibility == View.VISIBLE) {
                bottom.visibility = View.GONE
            }
        }
    }

    private fun getComments(){
        postViewModel.getComments(navPost.contentOfPost.post_id)
        postViewModel.getCommentsData().observe(viewLifecycleOwner){
            comments = it
            setAdapterComments()
        }
    }

    private fun setAdapterComments(){
        if (comments != null){

            val dataComments = comments?.content?.get(0)?.data
            val adapter = dataComments?.let { listData ->
                AdapterComments(listData, "11111111-1111-1111-1111-111111111111", object : OnClickComment{
                    override fun onClickComment(comment: Data, view: Int) {
                        when (view) {
                            0 -> {
                                postViewModel.setIsEditComment(false)
                            }
                            1 -> {
                                postViewModel.setIsEditComment(true)
                                postViewModel.selectCommentChange.value = comment
                                binding.textComment.setText(comment.comment)
                            }
                            2 -> {
                                deleteComment(comment.id)
                            }
                        }
                    }
                })
            }
            binding.commentRecycler.adapter = adapter
        }
    }

    private fun subscription(){
        /// Все останавливается на том, что не понятно чей это пост,
        // сам на себя юзер не может подписываться, надо сранивать id
    }

    private fun sendComment(){
        val textComment = binding.textComment.text.toString()
        if (postViewModel.validCommentSend(textComment)){
            val comment = AddComment(
                comment = textComment,
                post_id = navPost.contentOfPost.post_id
            )
            postViewModel.addComment(comment)
            getComments()
            binding.textComment.text?.clear()
            clearFocusEditText()
        } else {
            postViewModel.showSnackBar(requireView(), "Введите комментарий...")
        }
    }

    private fun clearFocusEditText(){
        binding.textComment.clearFocus()
        val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun updateComment(){

        val comment = postViewModel.selectCommentChange.value
        binding.textComment.setText(comment?.comment ?: "")
        val changeCommentText = binding.textComment.text.toString()

        val validChangeComment = comment?.let {
            postViewModel.validCommentChange(changeCommentText, it)
        }
        if (validChangeComment == true){
            val changeComments = ChangeComment(
                id = comment.id,
                comment = changeCommentText,
                post_id = navPost.contentOfPost.post_id
            )
            postViewModel.changeComment(changeComments)
            binding.textComment.text?.clear()
            postViewModel.setIsEditComment(false)
            getComments()
        }
    }

    private fun deleteComment(commentId:String){
        postViewModel.deleteComment(commentId)
        getComments()
    }

    private fun setMyStarsPost(){
    }

    private fun updateMyStarsPost(){
    }

    private fun showPost(){
        val content = navPost.contentOfPost
        binding.namePost.text = content.title
        binding.starsPost.text = content.countStar.toString()
        binding.descriptionPost.text = content.description

        binding.subscribe.visibility = View.GONE
        binding.deletePost.visibility = View.GONE
        binding.editPost.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}