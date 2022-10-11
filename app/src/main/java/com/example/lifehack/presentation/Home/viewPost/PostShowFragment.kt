package com.example.lifehack.presentation.Home.viewPost

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lifehack.R
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Comments.AddComment.AddComment
import com.example.lifehack.data.entity.Comments.ChangeComment.ChangeComment
import com.example.lifehack.data.entity.Comments.Comments
import com.example.lifehack.data.entity.Comments.Data
import com.example.lifehack.databinding.FragmentHomeBinding
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
        super.onResume()
        sharedViewModel.getToken().observe(viewLifecycleOwner){
            postViewModel.setToken(it.accessToken)
            getComments()
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

        showPost()

        binding.backHome.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.sendComment.setOnClickListener {
            if (postViewModel.getIsEditComment().value == true){
                updateComment()
                postViewModel.showSnackBar(requireView(), "Обновление комментария...")
            } else {
                sendComment()
            }
        }

        binding.subscribe.setOnClickListener {
            subscription()
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
                AdapterComments(listData, object : OnClickComment{
                    override fun onClickComment(comment: Data, view: Int) {
                        when (view) {
                            0 -> {
                                postViewModel.setIsEditComment(false)
                            }
                            1 -> {
                                postViewModel.setIsEditComment(true)
                                postViewModel.selectCommentChange.value = comment
                            }
                        }
                    }
                })
            }
            binding.commentRecycler.adapter = adapter
        }
    }

    private fun subscription(){

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
        } else {
            postViewModel.showSnackBar(requireView(), "Введите комментарий...")
        }
    }

    private fun updateComment(){

        val comment = postViewModel.selectCommentChange.value
        binding.textComment.setText(comment?.comment ?: "")
        val changeCommentText = binding.textComment.text.toString()

        val validChange = comment?.let { postViewModel.validCommentChange(changeCommentText, it) }
        if (validChange == true){
            val changeComments = ChangeComment(
                id = comment.id,
                comment = changeCommentText,
                post_id = navPost.contentOfPost.post_id
            )
            postViewModel.changeComment(changeComments)
            binding.textComment.text?.clear()
            postViewModel.setIsEditComment(false)
        }
    }

    private fun getStarsOfPost(){

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}