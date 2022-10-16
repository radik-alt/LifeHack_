package com.example.lifehack.presentation.Friends.ViewFirends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lifehack.R
import com.example.lifehack.databinding.FragmentHomeBinding
import com.example.lifehack.databinding.FragmentViewFollowerBinding
import java.lang.RuntimeException


class ViewFollowerFragment : Fragment() {

    private var _binding: FragmentViewFollowerBinding?=null
    private val binding: FragmentViewFollowerBinding
        get() = _binding ?: throw RuntimeException("FragmentViewFollowerBinding == null")

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

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}