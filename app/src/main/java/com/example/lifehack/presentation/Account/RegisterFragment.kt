package com.example.lifehack.presentation.Account

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lifehack.R
import com.example.lifehack.data.entity.Auth.SingUpUser
import com.example.lifehack.databinding.FragmentLogInBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import java.lang.RuntimeException


class RegisterFragment : Fragment() {

    private var _binding: FragmentLogInBinding?=null
    private val binding:FragmentLogInBinding
        get() = _binding ?: throw RuntimeException("FragmentLogInBinding == null")

    private val viewModelRegister : RegisterViewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    private var uriImage: String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)

        hideBottomView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.register.setOnClickListener {
            registerAccount()
        }

        binding.backToAuthFragment.setOnClickListener {
            findNavController().popBackStack()
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

    private fun registerAccount(){
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (valid(firstName, lastName, email, password)){
            val user = SingUpUser(
                first_name = firstName,
                last_name = lastName,
                email = email,
                pass = password,
                repass = password,
                extension = "jpg",
                avatar = uriImage?: ""
            )
            viewModelRegister.singUp(user)
        } else {
            Snackbar.make(requireView(), "Введите данные во все поля...", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun valid(firstName:String, lastName: String, email:String, password:String): Boolean {
        if (firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            email.isNotBlank() &&
            password.isNotBlank()
        ) {
            return true
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}