package com.example.lifehack.presentation.Friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.lifehack.R
import com.example.lifehack.databinding.FragmentAddLifeHackBinding
import com.example.lifehack.databinding.FragmentFriendsBinding
import com.example.lifehack.presentation.Home.SharedTokenViewModel
import com.example.lifehack.presentation.adapter.FreindsAdapter.FriendsAdapter


class FriendsFragment : Fragment() {

    private var _binding: FragmentFriendsBinding?=null
    private val binding: FragmentFriendsBinding
        get() = _binding ?: throw RuntimeException("FragmentFriendsBinding == null")

    private val tokenViewModel : SharedTokenViewModel by activityViewModels()
    private val friendsViewModel : FriendsViewModel by lazy {
        ViewModelProvider(this)[FriendsViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        loader(true)
        tokenViewModel.getToken().observe(viewLifecycleOwner){
            friendsViewModel.setToken(it.accessToken)
            friendsViewModel.getFollowUsers()
            setAdapter()
        }
    }

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

    }

    private fun setAdapter(){
        friendsViewModel.getList().observe(viewLifecycleOwner){
            when (it){
                is FollowUsers.Success ->{
                    val friends = it.users.data
                    if (friends.isNotEmpty()){
                        binding.recyclerFriends.adapter = FriendsAdapter(friends)
                    } else {

                    }
                }
                is FollowUsers.Error -> {

                }
            }
            loader(false)
        }
    }

    private fun loader(load: Boolean){
        if (load){
            binding.recyclerFriends.visibility = View.GONE
            binding.errorLoaderFriends.visibility = View.GONE
            binding.progressFriends.visibility = View.VISIBLE
        } else {
            binding.recyclerFriends.visibility = View.VISIBLE
            binding.errorLoaderFriends.visibility = View.GONE
            binding.progressFriends.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}