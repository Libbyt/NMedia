package ru.netology.nmedia

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

@RequiresApi(Build.VERSION_CODES.R)
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel by viewModels<PostViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post -> binding.render(post) }

        binding.like?.setOnClickListener {
            viewModel.onLikeClicked()
        }
        binding.share?.setOnClickListener {
            viewModel.onShareClicked()
        }
    }

    private fun ActivityMainBinding.render(post: Post) {
        authorName.text = post.author
        content.text = post.content
        date.text = post.published
        postLink.text = post.link
        like.setImageResource(getLikeIconResId(post.likedByMe))
        share.setImageResource(getShareIconResId(post.sharedByMe))
        likeCounter.text = numberFormat(post.likes)
        if (post.shares > 0) shareCounter.text = numberFormat(post.shares)
    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp

    @DrawableRes
    private fun getShareIconResId(shared: Boolean) =
        if (shared) R.drawable.ic_shared_24dp else R.drawable.ic_share_24dp
}

