package com.example.lifehack.presentation.Home.viewPost

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.lifehack.R
import com.example.lifehack.databinding.DialogStarBinding
import com.example.lifehack.presentation.Home.SharedTokenViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogStars : BottomSheetDialogFragment() {

    private var _binding: DialogStarBinding?=null
    private val binding:DialogStarBinding
        get() = _binding ?: throw RuntimeException("")

    private val sharedTokenViewModel:SharedTokenViewModel by activityViewModels()
    private val starsViewModel:StarsViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        sharedTokenViewModel.getToken().observe(viewLifecycleOwner){
            starsViewModel.setToken(it)
        }
    }

    private var countStar = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogStarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rating.setOnRatingBarChangeListener { ratingBar, fl, b ->
            countStar = ratingBar.rating.toInt()
            when (countStar){
                5 -> {
                    binding.countStar.text = countStar.toString()
                }
            }
        }

        binding.sendStar.setOnClickListener {
            starsViewModel.setStar(countStar)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}