package com.example.lifehack.presentation.Home.editPost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.lifehack.R
import com.example.lifehack.databinding.FragmentEditPostBinding


class EditPostFragment : Fragment() {

    private var _binding: FragmentEditPostBinding?=null
    private val binding:FragmentEditPostBinding
        get() = _binding ?: throw RuntimeException("")

    private val navPost by navArgs<EditPostFragmentArgs>()
    private val editPostViewModel: EditPostViewModel by lazy {
        ViewModelProvider(this)[EditPostViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        setData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.update.setOnClickListener {
            TODO("Пока не работает, нужно поправить ручку")
        }
    }

    private fun setData(){
        val content = navPost.contentOfPost
        binding.title.setText(content.title)
        binding.description.setText(content.description)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}