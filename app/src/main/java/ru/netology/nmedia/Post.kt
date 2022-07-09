package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String?,
    val content: String?,
    val link: String,
    val published: String,
    var likes: Int = 0,
    var shares: Int = 0,
    val likedByMe: Boolean = false,
    val sharedByMe: Boolean = false
)