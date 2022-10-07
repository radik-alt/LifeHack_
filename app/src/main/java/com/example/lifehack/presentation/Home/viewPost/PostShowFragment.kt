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
import com.example.lifehack.data.entity.Comments.Comments
import com.example.lifehack.data.entity.Comments.Data
import com.example.lifehack.databinding.FragmentHomeBinding
import com.example.lifehack.databinding.FragmentPostShowBinding
import com.example.lifehack.presentation.Home.SharedTokenViewModel
import com.example.lifehack.presentation.adapter.AdapterComments.AdapterComments
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
    private var token: RequestToken?=null
    private var comments: Comments?=null


    override fun onResume() {
        super.onResume()
        sharedViewModel.getToken().observe(viewLifecycleOwner){
            token = it
            getComments()
        }
        getComments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostShowBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        hideBottomView()
        return binding.root
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showPost()

        binding.backHome.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.sendComment.setOnClickListener {
            sendComment()
        }

    }

    private fun getComments(){
        postViewModel.getCommentsData().observe(viewLifecycleOwner){
            comments = it
            setAdapterComments()
        }
        token?.let { tokenLet ->
            postViewModel.getComments(navPost.contentOfPost.post_id, tokenLet.refreshToken)
        }
    }

    private fun setAdapterComments(){
        if (comments != null){
            val dataComments = comments?.content?.get(0)?.data
            val adapter = dataComments?.let { AdapterComments(it) }
            binding.commentRecycler.adapter = adapter
        }
    }

    private fun subscription(){

    }

    private fun sendComment(){
        val textComment = binding.textComment.text.toString()
        if (textComment.isNotEmpty()){
            val comment = AddComment(
                comment = textComment,
                question_id = navPost.contentOfPost.post_id
            )
            token?.let { postViewModel.addComment(comment, it.accessToken) }
            getComments()
        } else {
            postViewModel.showSnackBar(requireView(), "Введите комментарий...")
        }
    }

    private fun updateComment(){

    }

    private fun setStarsPost(){

    }

    private fun updateStarsPost(){

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