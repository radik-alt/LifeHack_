package com.example.lifehack.presentation.Rating.viewRatingPost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lifehack.R
import com.example.lifehack.databinding.FragmentRatingBinding
import com.example.lifehack.databinding.FragmentViewTopPostBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.RuntimeException

class ViewTopPostFragment : Fragment() {

    private var _binding: FragmentViewTopPostBinding?=null
    private val binding: FragmentViewTopPostBinding
        get() = _binding ?: throw RuntimeException("FragmentViewTopPostBinding == null")

    private val argument by navArgs<ViewTopPostFragmentArgs>()

    override fun onResume() {
        super.onResume()
        hideBottomView()
        setData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewTopPostBinding.inflate(inflater, container, false)
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

    private fun setData(){
        val topPost = argument.topPost
        binding.namePostTop.text = topPost.title
        binding.descriptionPostTop.text = topPost.description
        binding.starsPostTop.text = topPost.count_star.toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backTop.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getComments(){

    }

    private fun addComment(){

    }

    private fun updateComment(){

    }

    private fun validFollower(){

    }

    private fun follow(){

    }

    private fun unfollow(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}