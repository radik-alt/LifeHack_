package com.example.lifehack.presentation.Account

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.lifehack.R
import com.example.lifehack.data.entity.Auth.AuthUser
import com.example.lifehack.databinding.FragmentLogInAccountBinding
import com.example.lifehack.presentation.Home.SharedTokenViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.RuntimeException


class LogInAccountFragment : Fragment() {

    private var _binding: FragmentLogInAccountBinding?=null
    private val binding: FragmentLogInAccountBinding
        get() = _binding ?: throw RuntimeException("FragmentLogInAccountBinding == null")

    private val loginViewModel: LogInViewModel by lazy {
        ViewModelProvider(this)[LogInViewModel::class.java]
    }

    private val sharedViewModel : SharedTokenViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        hideBottomView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerUser.setOnClickListener {
            findNavController().navigate(R.id.action_logInAccountFragment_to_logInFragment)
        }

        binding.auth.setOnClickListener {
            authUser()
        }
    }

    private fun authUser(){
        val login = binding.email.text.toString()
        val password = binding.password.text.toString()
        if (loginViewModel.valid(login, password)){
            loader(true)
            val user = AuthUser(
                login = login,
                pass = password
            )
            loginViewModel.authUser(user)
            loginViewModel.requestAuthData.observe(viewLifecycleOwner){
                loader(false)
                when (it){
                    is Auth.SuccessAuth -> {
                        sharedViewModel.setToken(it.requestToken)
                        Log.d("getLogAuth", it.requestToken.toString())
                        val action = LogInAccountFragmentDirections.actionLogInAccountFragmentToHomeFragment()
                        findNavController().navigate(action)
                    }
                    is Auth.ErrorAuth -> {
                        Log.d("getLogAuth", it.errorMessage)
                        loginViewModel.showSnackBar(requireView(), it.errorMessage)
                    }
                }
            }
        } else {
            loginViewModel.showSnackBar(requireView(), "Введите логин и пароль...")
        }
    }

    private fun loader(load:Boolean){
        if (load){
            binding.authText.visibility = View.GONE
            binding.authProgress.visibility = View.VISIBLE
        } else {
            binding.authText.visibility = View.VISIBLE
            binding.authProgress.visibility = View.GONE
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}