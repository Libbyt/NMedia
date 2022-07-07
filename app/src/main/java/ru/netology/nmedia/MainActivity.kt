package ru.netology.nmedia

import android.os.Build
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding

@RequiresApi(Build.VERSION_CODES.R)
class MainActivity : AppCompatActivity(R.layout.activity_main) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 0L,
            author = getString(R.string.post_title),
            content = getString(R.string.postText),
            link = getString(R.string.post_link),
            published = getString(R.string.post_date),
            likes = 999999,
            shares = 199990
        )

        binding.render(post)
        binding.like?.setOnClickListener {
            post.likedByMe = !post.likedByMe
            if (post.likedByMe) {
                post.likes++
                binding.likeCounter.text = numberFormat(post.likes)
            } else {
                post.likes--
                if (post.likes > 0) binding.likeCounter.text = numberFormat(post.likes)
                else {
                    binding.likeCounter.text = null
                }
            }
            binding.like.setImageResource(getLikeIconResId(post.likedByMe))
        }
        binding.share?.setOnClickListener {
            post.sharedByMe = !post.sharedByMe
            if (post.sharedByMe)
                binding.share.setImageResource(getShareIconResId(post.sharedByMe))
            post.shares++
            binding.shareCounter.text = numberFormat(post.shares)
        }
    }

    private fun ActivityMainBinding.render(post: Post) {
        authorName.text = post.author
        content.text = post.content
        date.text = post.published
        postLink.text = post.link
        like.setImageResource(getLikeIconResId(post.likedByMe))
        share.setImageResource(getShareIconResId(post.sharedByMe))
        if (post.likes > 0) likeCounter.text = numberFormat(post.likes)
        if (post.shares > 0) shareCounter.text = numberFormat(post.shares)
    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp

    @DrawableRes
    private fun getShareIconResId(shared: Boolean) =
        if (shared) R.drawable.ic_shared_24dp else R.drawable.ic_share_24dp
}

