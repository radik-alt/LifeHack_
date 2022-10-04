package com.example.lifehack.presentation.Home.viewPost

import android.os.Bundle
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
import com.example.lifehack.data.entity.Comments.Comments
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


    override fun onResume() {
        super.onResume()
        sharedViewModel.getToken().observe(viewLifecycleOwner){
            token = it
            getComments()
        }
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

        postViewModel.getComments().observe(viewLifecycleOwner){
//            setAdapterComments(it.content)
        }

    }

    private fun getComments(){
        token?.let { tokenLet ->
            postViewModel.getComments(navPost.contentOfPost.post_id, tokenLet.refreshToken)
        }
    }

    private fun setAdapterComments(listComments: Comments){
//        val adapter = AdapterComments(listComments)
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