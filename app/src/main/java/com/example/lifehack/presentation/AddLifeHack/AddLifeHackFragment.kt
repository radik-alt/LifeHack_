package com.example.lifehack.presentation.AddLifeHack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.lifehack.R
import com.example.lifehack.data.entity.Auth.RequestToken
import com.example.lifehack.data.entity.Posts.OnePost.CreatePost
import com.example.lifehack.databinding.FragmentAddLifeHackBinding
import com.example.lifehack.presentation.Home.SharedTokenViewModel
import com.example.lifehack.presentation.adapter.AdapterTags.TagsAdapters
import com.example.lifehack.presentation.adapter.intreface.OnClickTags
import com.google.android.material.snackbar.Snackbar


class AddLifeHackFragment : Fragment() {

    private var _binding: FragmentAddLifeHackBinding?=null
    private val binding: FragmentAddLifeHackBinding
        get() = _binding ?: throw RuntimeException("FragmentAddLifeHackBinding == null")

    private val tags = ArrayList<String>()
    private val filesImage = ArrayList<String>()
    private val createViewModel : CreatePostViewModel by lazy {
        ViewModelProvider(this)[CreatePostViewModel::class.java]
    }

    private val tokenViewModel : SharedTokenViewModel by activityViewModels()
    private var token: RequestToken ?= null

    override fun onResume() {
        super.onResume()
        tokenViewModel.getToken().observe(viewLifecycleOwner){
            token = it
        }
        getSelectedImage()
        getSelectedTags()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddLifeHackBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        setAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.create.setOnClickListener {
            createPost()
        }

        binding.imagePost.setOnClickListener {
            val imageIntent = Intent(Intent.ACTION_GET_CONTENT)
            imageIntent.type = "image/*"
            getImage.launch(imageIntent)
        }

    }

    private fun setAdapter(){
        val listTags = ArrayList<String>()
        listTags.add("Дома")
        listTags.add("Еда")
        listTags.add("Спорт")
        listTags.add("Дети")
        listTags.add("Техника")

        binding.listTags.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.listTags.adapter = TagsAdapters(listTags, object : OnClickTags{
            override fun clickTags(tag: String) {
                createViewModel.addTags(tag)
                Log.d("GetTagSelected", tags.toString())
            }
        })
    }


    private val getImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        val image = it.data?.data
        Glide.with(requireContext())
            .load(image)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.imagePost)

        if (image != null){
            createViewModel.addImage(image.toString())
        }
    }


    private fun createPost(){
        val title = binding.title.text.toString()
        val desc = binding.description.text.toString()
        if (valid(title, desc)){
            val post = CreatePost(
                title = title,
                description = desc,
                tags = tags,
                files = filesImage
            )
            token?.let { createViewModel.createPost(post, it.accessToken) }
        } else {
            Snackbar.make(requireView(), "Введите название и описание", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun getSelectedImage(){
        createViewModel.getImage().observe(viewLifecycleOwner){
            filesImage.clear()
            filesImage.addAll(it)
        }
    }

    private fun getSelectedTags(){
        createViewModel.getTags().observe(viewLifecycleOwner){
            tags.clear()
            tags.addAll(it)
        }
    }

    private fun valid(title:String, description: String): Boolean{
        if (title.isNotEmpty() && description.isNotEmpty()) {
            return true
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}