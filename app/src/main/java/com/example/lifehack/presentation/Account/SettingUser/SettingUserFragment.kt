package com.example.lifehack.presentation.Account.SettingUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lifehack.R
import com.example.lifehack.data.Utils
import com.example.lifehack.data.entity.User.DataUser
import com.example.lifehack.databinding.FragmentSettingUserBinding
import com.example.lifehack.presentation.Home.SharedTokenViewModel
import com.example.lifehack.presentation.adapter.AdapterMyLifeHacks.AdapterMyLifeHacks
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.RuntimeException


class SettingUserFragment : Fragment() {

    private var _binding: FragmentSettingUserBinding?=null
    private val binding: FragmentSettingUserBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingUserBinding == null")


    private val sharedTokenViewModel:SharedTokenViewModel by activityViewModels()
    private val settingViewModel : SettingViewModel by lazy {
        ViewModelProvider(this)[SettingViewModel::class.java]
    }

    private var adapterMyLifeHacks:AdapterMyLifeHacks ?= null

    override fun onResume() {
        super.onResume()
        hideBottomView()
        val token = sharedTokenViewModel.getToken().value
        if (token != null)
            settingViewModel.setToken(token)
        else
            findNavController().popBackStack()

        getDataUser()
        getDataPosts()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingUserBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logout.setOnClickListener {
            settingViewModel.logout()
            findNavController().navigate(R.id.action_settingUserFragment_to_logInAccountFragment)
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getDataUser(){
        settingViewModel.getDataUser(Utils.user_default)
        settingViewModel.getDataUser().observe(viewLifecycleOwner){
            setData(it)
        }
    }

    private fun getDataPosts() {
        settingViewModel.getPostUser()
        settingViewModel.getDataPost().observe(viewLifecycleOwner){
            adapterMyLifeHacks?.setData(it.content)
        }
    }


    private fun setData(user: DataUser){
        binding.firstName.setText(user.data[0].firstName)
        binding.lastName.setText(user.data[0].lastName)

        adapterMyLifeHacks = AdapterMyLifeHacks()
        binding.recyclerMyLifeHacks.adapter = adapterMyLifeHacks
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}