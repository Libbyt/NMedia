package ru.netology.nmedia

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
import ru.netology.nmedia.viewModel.PostViewModel

@RequiresApi(Build.VERSION_CODES.R)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter(viewModel)
        binding.postRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.save.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
            }
        }
        binding.contentCancelEditButton.setOnClickListener{
            with(binding.contentEditText, ) {
                setText("")
                hideKeyboard()
            }
            with(binding.group) {
                visibility = View.GONE
            }
        }
        viewModel.currentPost.observe(this) {currentPost ->
            with(binding.contentEditText){
                val content = currentPost?.content
                setText(content)
                if (content != null) {
                    requestFocus()
                    showKeyboard()
                }
                else {
                    clearFocus()
                    hideKeyboard()
                }
            }
        }
    }
}

