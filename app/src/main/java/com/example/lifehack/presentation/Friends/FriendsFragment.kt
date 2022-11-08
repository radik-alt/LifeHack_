package com.example.lifehack.presentation.Friends

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lifehack.R
import com.example.lifehack.data.entity.Follow.Data
import com.example.lifehack.databinding.FragmentAddLifeHackBinding
import com.example.lifehack.databinding.FragmentFriendsBinding
import com.example.lifehack.presentation.Home.SharedTokenViewModel
import com.example.lifehack.presentation.adapter.FreindsAdapter.FriendsAdapter
import com.example.lifehack.presentation.adapter.intreface.OnClickFollower
import com.google.android.material.bottomnavigation.BottomNavigationView


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
        showBottomView()
        loader(true)
        tokenViewModel.getToken().observe(viewLifecycleOwner){
            friendsViewModel.setToken(it.accessToken)
            friendsViewModel.getFollowUsers()
            getFollowerUser()
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

        binding.searchFollower.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(searchText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (searchText != null && searchText.isNotEmpty() && friendsViewModel.getUsersList().isNotEmpty()) {
                    friendsViewModel.getSearchFollower(searchText)
                    getFollowerUser()
                } else if (searchText?.isEmpty() == true && friendsViewModel.getUsersList().isNotEmpty()) {
                    friendsViewModel.getFollowUsers()
                    getFollowerUser()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

    }

    private fun showBottomView(){
        val fragmentActivity = activity
        if (activity != null){
            val bottom = fragmentActivity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            if (bottom != null && bottom.visibility == View.GONE) {
                bottom.visibility = View.VISIBLE
            }
        }
    }

    private fun getFollowerUser(){
        friendsViewModel.getList().observe(viewLifecycleOwner){
            val isSearch = friendsViewModel.getIsSearch().value
            when (it){
                is FollowUsers.Success ->{
                    val friends = it.users
                    if (!friends.isNullOrEmpty()){
                        setAdapter(friends)
                    } else {
                        loader(false, "Нет подписок! Скорее заводи новые)")
                    }
                }
                is FollowUsers.Search -> {
                    val friends = it.users
                    if (!friends.isNullOrEmpty()){
                        setAdapter(friends)
                    } else {
                        loader(false, "Таких юзеров нет...")
                    }
                }
                is FollowUsers.Error -> {
                    loader(false, it.error)
                }
            }
        }
    }

    private fun setAdapter(friends: List<Data>){
        binding.recyclerFriends.adapter = FriendsAdapter(friends, object : OnClickFollower{
            override fun onClickFollower(data: Data, delete:Boolean) {
                if (delete){
                    deleteFollower(data.followedId)
                    friendsViewModel.snackBar(requireView(), "Удаление подписчика...")
                } else {
                    val action = FriendsFragmentDirections.actionFriendsFragmentToViewFollowerFragment(data)
                    findNavController().navigate(action)
                }
            }
        })
        loader(false)
    }

    private fun deleteFollower(followId: String){
        friendsViewModel.deleteFollowUser(followId)
    }

    private fun loader(load: Boolean, error:String?=null){

        when {
            load -> {
                binding.recyclerFriends.visibility = View.GONE
                binding.errorLoaderFriends.visibility = View.GONE
                binding.progressFriends.visibility = View.VISIBLE
            }
            else -> {
                if (error == null){
                    binding.recyclerFriends.visibility = View.VISIBLE
                    binding.errorLoaderFriends.visibility = View.GONE
                    binding.progressFriends.visibility = View.GONE
                } else {
                    binding.recyclerFriends.visibility = View.GONE
                    binding.errorLoaderFriends.visibility = View.VISIBLE
                    binding.errorLoaderFriends.text = error
                    binding.progressFriends.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}