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
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.RuntimeException


class ViewFollowerFragment : Fragment() {

    private var _binding: FragmentViewFollowerBinding?=null
    private val binding: FragmentViewFollowerBinding
        get() = _binding ?: throw RuntimeException("FragmentViewFollowerBinding == null")


    override fun onResume() {
        super.onResume()
        hideBottomView()
    }

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