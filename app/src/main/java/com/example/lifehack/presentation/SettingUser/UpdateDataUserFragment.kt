package com.example.lifehack.presentation.SettingUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lifehack.R
import com.example.lifehack.databinding.FragmentSettingUserBinding
import com.example.lifehack.databinding.FragmentUpdateDataUserBinding


class UpdateDataUserFragment : Fragment() {

    private var _binding:FragmentUpdateDataUserBinding?=null
    private val binding:FragmentUpdateDataUserBinding
        get() = _binding ?: throw RuntimeException("")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateDataUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateDataUser.setOnClickListener {
            TODO("Нужен email для обновления данных")
        }
    }

    private fun valid(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}