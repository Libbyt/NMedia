package ru.netology.nmedia.adapter

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding

typealias OnPostClicked = (Post) -> Unit

@RequiresApi(Build.VERSION_CODES.R)
class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class ViewHolder(
        private val binding: PostListItemBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            val menu = binding.menu
            PopupMenu(itemView.context, binding.menu).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener {menuItem ->
                    when(menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit ->{
                            val activity =  itemView.context as Activity
                            activity.findViewById<Group>(R.id.group).visibility = View.VISIBLE
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
                setOnDismissListener() {
                    menu.isChecked = false
                }
            }
        }

        init {
            binding.like.setOnClickListener {
                listener.onLikeClicked(post)
            }
            binding.share.setOnClickListener {
                listener.onShareClicked(post)
            }
            binding.menu.addOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    popupMenu.show()
                }
            }
        }

        fun bind(post: Post) {
            this.post = post
            with(binding) {
                authorName.text = post.author
                content.text = post.content
                date.text = post.published
//                like.setButtonDrawable(getLikeIconResId(post.likedByMe))
//                likeCounter.text = numberFormat(post.likes)
                like.text = post.likes.toString()
                like.isChecked = post.likedByMe
                //TODO исправить потом аналогично для Share'а как у лайка
                share.text = post.shares.toString()
                share.isChecked = post.sharedByMe
//                menu.isChecked = post.menuActive
//                share.setImageResource (getShareIconResId(post.sharedByMe))
//                if (post.shares > 0) shareCounter.text = numberFormat(post.shares)

            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}