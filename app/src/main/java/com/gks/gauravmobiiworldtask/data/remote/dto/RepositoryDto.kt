package com.gks.gauravmobiiworldtask.data.remote.dto

import com.gks.gauravmobiiworldtask.domain.model.Owner
import com.gks.gauravmobiiworldtask.domain.model.Repository

data class RepositoryDto(
    val id: Int,
    val name: String,
    val full_name: String,
    val owner: OwnerDto,
    val html_url: String,
    val description: String?,
    val fork: Boolean,
    val url: String,
    val stargazers_count: Int
)

data class OwnerDto(
    val login: String,
    val id: Int,
    val avatar_url: String
)

fun RepositoryDto.toDomain(): Repository {
    return Repository(
        id, name, full_name, owner.toDomain(), html_url, description, fork, url,stargazers_count
    )
}

fun OwnerDto.toDomain(): Owner {
    return Owner(login, id, avatar_url)
}
