package com.example.lifehack.presentation.Search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lifehack.databinding.FragmentSearchBinding
import com.example.lifehack.presentation.Home.SharedTokenViewModel
import com.example.lifehack.presentation.adapter.AdapterPostHome
import com.example.lifehack.presentation.adapter.AdapterRecomnedation.AdapterRecommendation
import java.lang.RuntimeException


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding?=null
    private val binding: FragmentSearchBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchBinding == null")

    private val searchViewModel:SearchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }
    private val sharedTokenViewModel : SharedTokenViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        val token = sharedTokenViewModel.getToken().value
        if (token != null) {
            searchViewModel.setToken(token)
            searchViewModel.getApiRecommendationPosts()
            setAdapter()
        } else findNavController().popBackStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun setAdapter(){
        searchViewModel.getRecommendationPosts().observe(viewLifecycleOwner){
            binding.recommendationRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recommendationRecycler.adapter = AdapterRecommendation(it.content)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}