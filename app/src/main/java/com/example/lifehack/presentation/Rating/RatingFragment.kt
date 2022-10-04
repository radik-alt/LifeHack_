package com.example.lifehack.presentation.Rating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lifehack.R
import com.example.lifehack.data.entity.Posts.Content
import com.example.lifehack.data.entity.Posts.MainPost
import com.example.lifehack.databinding.FragmentLogInAccountBinding
import com.example.lifehack.databinding.FragmentRatingBinding
import com.example.lifehack.presentation.adapter.AdapterPostHome
import com.example.lifehack.presentation.adapter.intreface.OnClickPost
import java.lang.RuntimeException


class RatingFragment : Fragment() {

    private var _binding: FragmentRatingBinding?=null
    private val binding: FragmentRatingBinding
        get() = _binding ?: throw RuntimeException("FragmentRatingBinding == null")

    private var listHomePosts = ArrayList<Content?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatingBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
    }

    private fun setAdapter(){
        binding.listHomePosts.adapter = AdapterPostHome(listHomePosts, object: OnClickPost {
            override fun selectItemPost(post: Content) {
                findNavController().navigate(R.id.action_homeFragment_to_postShowFragment)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}