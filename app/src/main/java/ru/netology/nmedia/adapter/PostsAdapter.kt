package ru.netology.nmedia.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.numberFormat

@RequiresApi(Build.VERSION_CODES.R)
class PostsAdapter(
    private val onLikeClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, onLikeClicked, onShareClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }


    class ViewHolder(
        private val binding: PostListItemBinding,
        private val onLikeClicked: (Post) -> Unit,
        private val onShareClicked: (Post) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.like.setOnClickListener {
                onLikeClicked(post)
            }
            binding.share.setOnClickListener {
                onShareClicked(post)
            }

        }


        fun bind(post: Post) {
            this.post = post
            with(binding) {
                authorName.text = post.author
                content.text = post.content
                date.text = post.published
                postLink.text = post.link
                like.setImageResource(getLikeIconResId(post.likedByMe))
                share.setImageResource(getShareIconResId(post.sharedByMe))
                likeCounter.text = numberFormat(post.likes)
                if (post.shares > 0) shareCounter.text = numberFormat(post.shares)
            }
        }

        @DrawableRes
        private fun getLikeIconResId(liked: Boolean) =
            if (liked) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp

        @DrawableRes
        private fun getShareIconResId(shared: Boolean) =
            if (shared) R.drawable.ic_shared_24dp else R.drawable.ic_share_24dp


    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem

    }
}