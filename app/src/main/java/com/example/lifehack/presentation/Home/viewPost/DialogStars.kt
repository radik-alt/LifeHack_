package com.example.lifehack.presentation.Home.viewPost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.lifehack.R
import com.example.lifehack.databinding.DialogStarBinding
import com.example.lifehack.presentation.Home.SharedTokenViewModel
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

            binding.countStar.setTextColor(ratingColor())
            binding.countStar.text = countStar.toString()
        }

        binding.sendStar.setOnClickListener {
            if (validStars()){
                starsViewModel.setStar(countStar)
                dismiss()
            }
        }
    }

    private fun ratingColor(): Int {
        return when (countStar){
            1 -> {
                return requireContext().resources.getColor(R.color.errorArea)
            }
            2-> {
                return requireContext().resources.getColor(R.color.errorArea)
            }
            3-> {
                return requireContext().resources.getColor(R.color.middleArea)
            }
            4 -> {
                return requireContext().resources.getColor(R.color.goldColor)
            }
            5 -> {
                return requireContext().resources.getColor(R.color.topArea)
            }
            else -> {
                return requireContext().resources.getColor(R.color.topArea)
            }
        }
    }

    private fun validStars(): Boolean {
        return countStar != 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}