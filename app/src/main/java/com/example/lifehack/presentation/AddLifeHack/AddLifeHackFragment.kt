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
import com.example.lifehack.data.entity.TagsDTO
import com.example.lifehack.databinding.FragmentAddLifeHackBinding
import com.example.lifehack.presentation.Home.SharedTokenViewModel
import com.example.lifehack.presentation.adapter.AdapterTags.TagsAdapters
import com.example.lifehack.presentation.adapter.intreface.OnClickTags


class AddLifeHackFragment : Fragment() {

    private var _binding: FragmentAddLifeHackBinding?=null
    private val binding: FragmentAddLifeHackBinding
        get() = _binding ?: throw RuntimeException("FragmentAddLifeHackBinding == null")


    private val createViewModel : CreatePostViewModel by lazy {
        ViewModelProvider(this)[CreatePostViewModel::class.java]
    }
    private val tokenViewModel : SharedTokenViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        loader(true)
        tokenViewModel.getToken().observe(viewLifecycleOwner){
            createViewModel.setToken(it)
        }
        createViewModel.getListTags()
        setAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddLifeHackBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
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
        binding.listTags.layoutManager = GridLayoutManager(requireContext(), 4)
        createViewModel.listTag.observe(viewLifecycleOwner) {
            loader(false)
            binding.listTags.adapter = TagsAdapters(it, object : OnClickTags{
                    override fun clickTags(tag: TagsDTO) {
                        if (tag.select){
                            createViewModel.deleteTags(tag)
                        } else {
                            createViewModel.addTags(tag)
                        }
                        Log.d("GetTagSelected", tag.toString())
                    }
                })
        }
    }

    private fun loader(load:Boolean, size:Int?=null) {
        if (load){
            binding.loaderTags.visibility = View.VISIBLE
            binding.listTags.visibility = View.GONE
        } else {
            binding.loaderTags.visibility = View.GONE
            binding.listTags.visibility = View.VISIBLE
        }
    }

    private val getImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val image = it.data?.data
        Glide.with(requireContext())
            .load(image)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.imagePost)
    }


    private fun createPost(){
        val title = binding.title.text.toString()
        val descriptionPost = binding.description.text.toString()

        if (createViewModel.validCreatePost(title, descriptionPost)){
            val post = CreatePost(
                title = title,
                description = descriptionPost,
                tags = createViewModel.getTags(),
                files = createViewModel.getImage()
            )
            Log.d("CreatePostData", post.toString())
            createViewModel.createPost(post)
            createViewModel.snackBar(requireView(), "Пост успешно создан!")
            clear()
        } else {
            createViewModel.snackBar(requireView(), "Введите название и описание")
        }
    }

    private fun clear(){
        binding.title.text?.clear()
        binding.description.text?.clear()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}