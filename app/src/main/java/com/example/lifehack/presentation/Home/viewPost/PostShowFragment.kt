package com.example.lifehack.presentation.Home.viewPost

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehack.R
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
    private val starsViewModel:StarsViewModel by activityViewModels()

    private var comments: Comments?=null
    private var userId:String?=null


    override fun onResume() {
        hideBottomView()
        super.onResume()
        userId = sharedViewModel.getUserId()
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

        binding.starsPost.setOnClickListener {
            starsViewModel.setPostId(navPost.contentOfPost.post_id)
            val dialogStar = DialogStars()
            dialogStar.show(parentFragmentManager, dialogStar.tag)
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
            val action = PostShowFragmentDirections.actionPostShowFragmentToEditPostFragment(navPost.contentOfPost)
            findNavController().navigate(action)
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


    private fun deletePost(){
        postViewModel.deletePost(navPost.contentOfPost.post_id)
        findNavController().popBackStack()
        postViewModel.showSnackBar(requireView(), "???????? ${navPost.contentOfPost.title} ????????????!")
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
                AdapterComments(userId.toString(), object : OnClickComment{
                    override fun onClickComment(comment: Data, view: Int) {
                        when (view) {
                            0 -> {
                                postViewModel.setIsEditComment(false)
                            }
                            1 -> {
                                if (userId == comment.author_id){
                                    postViewModel.setIsEditComment(true)
                                    postViewModel.selectCommentChange.value = comment
                                    binding.textComment.setText(comment.comment)
                                }
                            }
                            2 -> {
                                deleteComment(comment.id)
                            }
                        }
                    }
                })
            }
            adapter?.setData(dataComments)
            binding.commentRecycler.adapter = adapter
        }
    }

    private fun subscription(){
        val postFollow = PostFollow(
            followed_id = userId.toString()
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
            postViewModel.showSnackBar(requireView(), "?????????????? ??????????????????????...")
        }
    }

    private fun clearFocusEditText(){
        binding.textComment.clearFocus()
        val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun updateComment(){

        val comment = postViewModel.selectCommentChange.value
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

    private fun showPost(){
        val content = navPost.contentOfPost
        binding.namePost.text = content.title
        binding.starsPost.text = content.count_star.toString()
        binding.ratingBar.rating = content.count_star.toFloat()
        binding.descriptionPost.text = content.description

        if (userId != content.user_id){
            binding.subscribe.visibility = View.VISIBLE
            binding.deletePost.visibility = View.GONE
            binding.editPost.visibility = View.GONE

            // ?????????????????? ?????????????? ?????? ???????? ????????????????
            binding.subscribe.text = "????????????????????"
            binding.subscribe.background = requireContext().getDrawable(R.drawable.dont_fun_button)

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