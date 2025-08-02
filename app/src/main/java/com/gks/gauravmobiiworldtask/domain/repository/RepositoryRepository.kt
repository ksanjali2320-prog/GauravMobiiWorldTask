package com.gks.gauravmobiiworldtask.domain.repository

import com.gks.gauravmobiiworldtask.data.local.entity.RepositoryEntity
import com.gks.gauravmobiiworldtask.domain.model.Repository

interface RepositoryRepository {
    suspend fun fetchAndCacheRepositories()
    suspend fun getPaginatedRepos(page: Int, size: Int): List<Repository>
    suspend fun getRepositoryDetail(id: Int): Repository
}
