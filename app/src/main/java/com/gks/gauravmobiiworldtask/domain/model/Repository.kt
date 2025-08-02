package com.gks.gauravmobiiworldtask.domain.model

data class Repository(
    val id: Int,
    val name: String,
    val fullName: String,
    val owner: Owner,
    val htmlUrl: String,
    val description: String?,
    val fork: Boolean,
    val url: String,
    val stargazers_count: Int,
    var addToBookMark: Boolean = false
)

data class Owner(
    val login: String,
    val id: Int,
    val avatarUrl: String
)
