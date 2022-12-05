package com.example.lifehack.presentation.Rating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lifehack.R
import com.example.lifehack.data.entity.Stars.Top100.Top100Item
import com.example.lifehack.databinding.FragmentRatingBinding
import com.example.lifehack.presentation.Home.SharedTokenViewModel
import com.example.lifehack.presentation.adapter.AdapterTop100.AdapterTop100
import com.example.lifehack.presentation.adapter.intreface.OnClickTopPost
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.RuntimeException


class RatingFragment : Fragment() {

    private var _binding: FragmentRatingBinding?=null
    private val binding: FragmentRatingBinding
        get() = _binding ?: throw RuntimeException("FragmentRatingBinding == null")

    private val sharedTokenViewModel: SharedTokenViewModel by activityViewModels()
    private val ratingViewModel: RatingViewModel by lazy {
        ViewModelProvider(this)[RatingViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        showBottomView()
        val token = sharedTokenViewModel.getToken()
        token.value?.let {
            ratingViewModel.setToken(it)
        }
        workListTags()
        setAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatingBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun setAdapter(){
        ratingViewModel.setTop100Post()
        ratingViewModel.getTop100Post().observe(viewLifecycleOwner){
            val adapterTop100 = AdapterTop100(it, object : OnClickTopPost{
                override fun clickTopPost(post: Top100Item) {
                    val action = RatingFragmentDirections.actionRatingFragmentToViewTopPostFragment(post)
                    findNavController().navigate(action)
                }
            })
            binding.listTopPosts.adapter = adapterTop100
        }
    }

    private fun workListTags(){
        ratingViewModel.getListTagsRequest()
        ratingViewModel.getListTag().observe(viewLifecycleOwner){
            setListTags(it)
            binding.spinnerTag.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectTag = adapter?.getItemAtPosition(position)
                    ratingViewModel.setTag(selectTag.toString())
                    setAdapter()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }
    }

    private fun setListTags(listTags:ArrayList<String>){
        val adapterTags = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listTags)
        adapterTags.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTag.adapter = adapterTags
        binding.spinnerTag.setSelection(0)
        binding.spinnerTag.isEnabled = true
    }


    private fun showBottomView(){
        val fragmentActivity = activity
        if (activity != null){
            val bottom = fragmentActivity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            if (bottom != null && bottom.visibility == View.GONE) {
                bottom.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}