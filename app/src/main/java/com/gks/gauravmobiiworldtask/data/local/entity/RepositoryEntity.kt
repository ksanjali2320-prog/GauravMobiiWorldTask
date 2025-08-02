package com.gks.gauravmobiiworldtask.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gks.gauravmobiiworldtask.domain.model.Owner
import com.gks.gauravmobiiworldtask.domain.model.Repository

@Entity(tableName = "repositories")
data class RepositoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val fullName: String,
    val login: String,
    val ownerId: Int,
    val avatarUrl: String,
    val htmlUrl: String,
    val description: String?,
    val fork: Boolean,
    val url: String,
    val stargazers_count: Int,
    val addToBookMark: Boolean = false
)

fun Repository.toEntity(): RepositoryEntity = RepositoryEntity(
    id, name, fullName, owner.login, owner.id, owner.avatarUrl, htmlUrl, description, fork, url,stargazers_count,addToBookMark
)

fun RepositoryEntity.toDomain(): Repository = Repository(
    id, name, fullName,
    Owner(login, ownerId, avatarUrl),
    htmlUrl, description, fork, url,stargazers_count,addToBookMark
)
