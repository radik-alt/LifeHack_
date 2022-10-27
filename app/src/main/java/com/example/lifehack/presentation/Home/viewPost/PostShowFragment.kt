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
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.R
import com.example.lifehack.data.Utils
import com.example.lifehack.data.entity.Comments.AddComment.AddComment
import com.example.lifehack.data.entity.Comments.ChangeComment.ChangeComment
import com.example.lifehack.data.entity.Comments.Comments
import com.example.lifehack.data.entity.Comments.Data
import com.example.lifehack.data.entity.Follow.postFollow.PostFollow
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
    private var userdId = Utils.user_default


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
            if (binding.descriptionPost.maxLines == Int.MAX_VALUE){
                binding.descriptionPost.maxLines = 5
            } else {
                binding.descriptionPost.maxLines = Int.MAX_VALUE
            }
            clearFocusEditText()
        }

        binding.deletePost.setOnClickListener {
            deletePost()
        }

        binding.editPost.setOnClickListener {
            editPost()
        }

        binding.commentRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            var scroll = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                scroll += dy
                if (scroll > 100){
                    binding.titleComments.visibility = View.GONE
                }

                if (scroll <= 0){
                    binding.titleComments.visibility = View.VISIBLE
                }
            }
        })


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

    private fun editPost(){
        val action = PostShowFragmentDirections.actionPostShowFragmentToEditPostFragment(navPost.contentOfPost)
        findNavController().navigate(action)
    }

    private fun deletePost(){
        postViewModel.deletePost(navPost.contentOfPost.post_id)
        findNavController().popBackStack()
        postViewModel.showSnackBar(requireView(), "Пост ${navPost.contentOfPost.title} удален!")
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
                AdapterComments(listData, userdId, object : OnClickComment{
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
        val postFollow = PostFollow(
            followed_id = userdId
        )
        postViewModel.subscribe(postFollow)
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

        if (userdId != content.user_id){
            binding.subscribe.visibility = View.VISIBLE
            binding.deletePost.visibility = View.GONE
            binding.editPost.visibility = View.GONE
        } else {
            binding.subscribe.visibility = View.GONE
            binding.deletePost.visibility = View.VISIBLE
            binding.editPost.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}