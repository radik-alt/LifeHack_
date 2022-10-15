package com.example.lifehack.presentation.Account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.lifehack.R
import com.example.lifehack.data.entity.Auth.SingUpUser
import com.example.lifehack.databinding.FragmentLogInBinding
import com.example.lifehack.presentation.Home.SharedTokenViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import java.lang.RuntimeException


class RegisterFragment : Fragment() {

    private var _binding: FragmentLogInBinding?=null
    private val binding:FragmentLogInBinding
        get() = _binding ?: throw RuntimeException("FragmentLogInBinding == null")


    private val sharedViewModel : SharedTokenViewModel by activityViewModels()
    private val viewModelRegister : RegisterViewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    private var uriImage: String?=null

    override fun onResume() {
        super.onResume()
        getImageData()
    }

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

        binding.registerImage.setOnClickListener {
            val imageIntent = Intent().apply {
                action = Intent.ACTION_GET_CONTENT
                type = "image/*"
            }
            getImage.launch(imageIntent)
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

        if (viewModelRegister.valid(firstName, lastName, email, password)){
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
            viewModelRegister.requestRegistertData.observe(viewLifecycleOwner){
                when (it){
                    is Register.SuccessRegister ->{
                        sharedViewModel.setToken(it.requestToken)
                        findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
                    }
                    is Register.ErrorRegister -> {
                        viewModelRegister.showSnackBar(requireView(), it.error)
                    }
                }
            }
        } else {
            viewModelRegister.showSnackBar(requireView(), "Не все поля заполнены...")
        }
    }

    private val getImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        val image = it.data?.data
        if (image != null){
            setImage(image.toString())
            getImageData()
        }
    }

    private fun setImage(image:String){
        viewModelRegister.setImageUser(image)
    }

    private fun getImageData(){
        viewModelRegister.getImageUser().observe(viewLifecycleOwner){
            uriImage = it
            Glide.with(requireContext())
                .load(uriImage)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.registerImage)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}