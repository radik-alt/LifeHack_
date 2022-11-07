package com.example.lifehack.presentation.Friends.ViewFirends

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
import com.example.lifehack.databinding.FragmentHomeBinding
import com.example.lifehack.databinding.FragmentViewFollowerBinding
import com.example.lifehack.presentation.Home.SharedTokenViewModel
import com.example.lifehack.presentation.adapter.AdapterMyLifeHacks.AdapterMyLifeHacks
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.RuntimeException


class ViewFollowerFragment : Fragment() {

    private var _binding: FragmentViewFollowerBinding?=null
    private val binding: FragmentViewFollowerBinding
        get() = _binding ?: throw RuntimeException("FragmentViewFollowerBinding == null")

    private val dataUser = navArgs<ViewFollowerFragmentArgs>()
    private val sharedTokenViewModel:SharedTokenViewModel by activityViewModels()
    private val viewFriendsViewModel : ViewFriendsViewModel by lazy {
        ViewModelProvider(this)[ViewFriendsViewModel::class.java]
    }
    private var adapterMyLifeHacks:AdapterMyLifeHacks ?= null

    override fun onResume() {
        super.onResume()
        hideBottomView()
        setData()
        val token = sharedTokenViewModel.getToken().value
        if (token != null) {
            viewFriendsViewModel.setToken(token)
            viewFriendsViewModel.getAllPostUser(dataUser.value.dataUser.followedId)
            setAdapter()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backAllFollowUsers.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.deleteFriends.setOnClickListener {
            viewFriendsViewModel.snackBar(requireView(), "Удалил из друзей :(")
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

    private fun setData(){
        val content = dataUser.value.dataUser
        binding.nameUser.text = content.followedName
        adapterMyLifeHacks = AdapterMyLifeHacks()
        binding.lifeHackUsers.adapter = adapterMyLifeHacks
    }

    private fun setAdapter(){
        viewFriendsViewModel.getPostsOfUser().observe(viewLifecycleOwner){
            adapterMyLifeHacks?.setData(it.content)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}