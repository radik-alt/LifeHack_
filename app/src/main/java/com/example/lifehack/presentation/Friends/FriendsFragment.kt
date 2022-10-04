package com.example.lifehack.presentation.Friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lifehack.R
import com.example.lifehack.databinding.FragmentAddLifeHackBinding
import com.example.lifehack.databinding.FragmentFriendsBinding
import com.example.lifehack.presentation.adapter.FreindsAdapter.FriendsAdapter


class FriendsFragment : Fragment() {

    private var _binding: FragmentFriendsBinding?=null
    private val binding: FragmentFriendsBinding
        get() = _binding ?: throw RuntimeException("FragmentFriendsBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
    }

    private fun setAdapter(){
        val listFriends = ArrayList<Int>()
        for (i in 0..100){
            listFriends.add(i)
        }

        binding.recyclerFriends.adapter = FriendsAdapter(listFriends)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}