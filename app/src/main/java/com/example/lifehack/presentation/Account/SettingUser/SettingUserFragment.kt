package com.example.lifehack.presentation.Account.SettingUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lifehack.R
import com.example.lifehack.databinding.FragmentRatingBinding
import com.example.lifehack.databinding.FragmentSettingUserBinding
import java.lang.RuntimeException


class SettingUserFragment : Fragment() {

    private var _binding: FragmentSettingUserBinding?=null
    private val binding: FragmentSettingUserBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingUserBinding == null")

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
            findNavController().navigate(R.id.action_settingUserFragment_to_logInAccountFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}