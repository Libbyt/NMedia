package ru.netology.nmedia.data.impl


import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository


class InMemoryPostRepository : PostRepository {

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }
    override val data: MutableLiveData<List<Post>>

    init {
        val initialPosts = List(1000) { index ->
            Post(
                id = index + 1L,
                author = "Нетология",
                content = "Контент поста №${index + 1}",
                link = "Ссылка",
                published = "Дата",
                likes = 999,
                shares = 995
            )
        }
        data = MutableLiveData(initialPosts)
    }


    override fun like(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId) post.copy(
                likedByMe = !post.likedByMe,
                likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
            )
            else post
        }
    }

    override fun share(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId) post.copy(
                sharedByMe = if (!post.sharedByMe) !post.sharedByMe else post.sharedByMe,
                shares = post.shares + 1
            )
            else post
        }
    }


}