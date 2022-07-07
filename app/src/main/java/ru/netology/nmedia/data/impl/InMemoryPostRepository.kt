package ru.netology.nmedia.data.impl

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository: PostRepository {

     @SuppressLint("RestrictedApi", "ResourceType")
     override val data = MutableLiveData<Post>(
        Post(
            id = 0L,
            author = Resources.getSystem().getString(R.string.post_title),
            content = Resources.getSystem().getString(R.string.postText),
            link = Resources.getSystem().getString(R.string.post_link),
            published = Resources.getSystem(). getString(R.string.post_date),
            likes = 999,
            shares = 995
        )
    )

    @RequiresApi(Build.VERSION_CODES.R)
    override fun like() {
        val currentPost = checkNotNull(data.value) {"Data value should not be null"}
        val likedPost = currentPost.copy(
            likedByMe = !currentPost.likedByMe,
            likes = if (currentPost.likedByMe) currentPost.likes-1
            else currentPost.likes+1
        )
        data.value = likedPost
    }
    override fun share() {
        val currentPost = checkNotNull(data.value)
        val sharedPost = currentPost.copy(
            sharedByMe = if (!currentPost.sharedByMe) {!currentPost.sharedByMe}
            else {currentPost.sharedByMe},
            shares = currentPost.shares+1
        )
        data.value = sharedPost
    }
}