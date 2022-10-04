package com.example.lifehack.presentation.Home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lifehack.R
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Posts.Content
import com.example.lifehack.data.entity.Posts.MainPost
import com.example.lifehack.databinding.FragmentHomeBinding
import com.example.lifehack.databinding.FragmentLogInAccountBinding
import com.example.lifehack.databinding.FragmentLogInBinding
import com.example.lifehack.presentation.adapter.AdapterPostHome
import com.example.lifehack.presentation.adapter.intreface.OnClickPost
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.RuntimeException


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding?=null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding == null")

    private val homeViewModel : HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private val sharedViewModel : SharedTokenViewModel by activityViewModels()
    private var token: RequestToken?=null
    private var listHomePosts = ArrayList<Content?>()

    override fun onResume() {
        super.onResume()
        loader(true)
        sharedViewModel.getToken().observe(viewLifecycleOwner){
            token = it
            token?.let { tokenLet ->
                homeViewModel.getPosts(tokenLet.accessToken)
            }
        }

        homeViewModel.getPostsData().observe(viewLifecycleOwner){
            listHomePosts.clear()
            val content = it.content
            content?.let { it1 ->
                listHomePosts.addAll(it1)
            }
            setAdapter()
            loader(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        showBottomView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarHome.root.findViewById<CircleImageView>(R.id.imageUser).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingUserFragment)
        }

    }

    private fun loader(load: Boolean){
        if (load){
            binding.textDefinedPosts.visibility = View.GONE
            binding.progressHomePost.visibility = View.VISIBLE
            binding.listHomePosts.visibility = View.GONE
        } else {
            if (listHomePosts.isEmpty()){
                binding.textDefinedPosts.visibility = View.VISIBLE
                binding.progressHomePost.visibility = View.GONE
                binding.listHomePosts.visibility = View.GONE
            } else {
                binding.textDefinedPosts.visibility = View.GONE
                binding.progressHomePost.visibility = View.GONE
                binding.listHomePosts.visibility = View.VISIBLE
            }

        }
    }

    private fun setAdapter(){
        binding.listHomePosts.adapter = AdapterPostHome(listHomePosts, object: OnClickPost{
            override fun selectItemPost(post: Content) {
                val action = HomeFragmentDirections.actionHomeFragmentToPostShowFragment(post)
                findNavController().navigate(action)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}