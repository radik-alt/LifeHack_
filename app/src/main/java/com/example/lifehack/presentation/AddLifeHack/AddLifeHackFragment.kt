package com.example.lifehack.presentation.AddLifeHack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.lifehack.R
import com.example.lifehack.data.entity.Posts.OnePost.CreatePost
import com.example.lifehack.data.entity.Tags
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

    override fun onResume() {
        super.onResume()
        tokenViewModel.getToken().observe(viewLifecycleOwner){
            createViewModel.setToken(it)
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
        val listTags = ArrayList<Tags>()
        listTags.add(Tags("Дома", false))
        listTags.add(Tags("Еда", false))
        listTags.add(Tags("Спорт", false))
        listTags.add(Tags("Дети", false))
        listTags.add(Tags("Техника", false))

        binding.listTags.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.listTags.adapter = TagsAdapters(listTags, object : OnClickTags{
            override fun clickTags(tag: Tags) {
                if (tag.select){
                    createViewModel.deleteTags(tag)
                } else {
                    createViewModel.addTags(tag)
                }
                Log.d("GetTagSelected", createViewModel.getTags().value.toString())
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
        val descriptionPost = binding.description.text.toString()

        if (createViewModel.validCreatePost(title, descriptionPost)){
            val post = CreatePost(
                title = title,
                description = descriptionPost,
                tags = tags,
                files = filesImage
            )
            createViewModel.createPost(post)
        } else {
            Snackbar.make(requireView(), "Введите название и описание", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun getSelectedImage(){
        createViewModel.getImage().observe(viewLifecycleOwner){
            filesImage.clear()
            filesImage.addAll(it)
            Glide.with(requireContext())
                .load(filesImage[0])
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.imagePost)
        }
    }

    private fun getSelectedTags(){
        createViewModel.getTags().observe(viewLifecycleOwner){
            tags.clear()
            tags.addAll(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}