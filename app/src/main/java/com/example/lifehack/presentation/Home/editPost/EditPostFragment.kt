package com.example.lifehack.presentation.Home.editPost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lifehack.R
import com.example.lifehack.databinding.FragmentEditPostBinding


class EditPostFragment : Fragment() {

    private var _binding: FragmentEditPostBinding?=null
    private val binding:FragmentEditPostBinding
        get() = _binding ?: throw RuntimeException("")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}