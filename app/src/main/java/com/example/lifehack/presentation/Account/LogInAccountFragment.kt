package com.example.lifehack.presentation.Account

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
import com.example.lifehack.data.entity.Auth.AuthUser
import com.example.lifehack.databinding.FragmentLogInAccountBinding
import com.example.lifehack.databinding.FragmentLogInBinding
import com.example.lifehack.presentation.Home.SharedTokenViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import java.lang.RuntimeException


class LogInAccountFragment : Fragment() {

    private var _binding: FragmentLogInAccountBinding?=null
    private val binding: FragmentLogInAccountBinding
        get() = _binding ?: throw RuntimeException("FragmentLogInAccountBinding == null")

    private val loginViewModel: LogInViewModel by lazy {
        ViewModelProvider(this)[LogInViewModel::class.java]
    }

    private val sharedViewModel : SharedTokenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInAccountBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        hideBottomView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerUser.setOnClickListener {
            findNavController().navigate(R.id.action_logInAccountFragment_to_logInFragment)
        }

        binding.auth.setOnClickListener {
            val login = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (valid(login, password)){
                val user = AuthUser(
                    login = login,
                    pass = password
                )
                loginViewModel.authUser(user)
                loader(true)
                loginViewModel.requestToken.observe(viewLifecycleOwner){
                    Log.d("RequestTokenAuth", it.toString())
                    sharedViewModel.setToken(it)
                    loader(false)
                    findNavController().navigate(R.id.action_logInAccountFragment_to_homeFragment)
                }
            } else {
                Snackbar.make(requireView(), "Введите логин и пароль...", Snackbar.LENGTH_LONG).show()
            }
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

    private fun valid(login:String, password: String):Boolean{
        return login.isNotEmpty() && password.isNotEmpty()
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