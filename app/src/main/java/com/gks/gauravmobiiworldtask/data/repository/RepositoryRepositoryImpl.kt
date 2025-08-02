package com.gks.gauravmobiiworldtask.data.repository

import com.gks.gauravmobiiworldtask.data.local.dao.RepositoryDao
import com.gks.gauravmobiiworldtask.data.local.entity.RepositoryEntity
import com.gks.gauravmobiiworldtask.data.local.entity.toDomain
import com.gks.gauravmobiiworldtask.data.local.entity.toEntity
import com.gks.gauravmobiiworldtask.data.remote.api.GithubApi
import com.gks.gauravmobiiworldtask.data.remote.dto.toDomain
import com.gks.gauravmobiiworldtask.domain.model.Repository
import com.gks.gauravmobiiworldtask.domain.repository.RepositoryRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.collections.map

class RepositoryRepositoryImpl @Inject constructor(
    private val api: GithubApi,
    private val dao: RepositoryDao
) : RepositoryRepository {

    override suspend fun fetchAndCacheRepositories() {
        val response = api.getRepositories()
        dao.clearAll()
        dao.insertAll(response.map { it.toDomain().toEntity() })
    }

    override suspend fun getPaginatedRepos(page: Int, size: Int): List<Repository> {
        return dao.getPaginatedRepos(size, page * size).map { it.toDomain() }
    }

    override suspend fun getRepositoryDetail(id: Int): Repository {
        return dao.getRepositoryById(id)
            .first()
            .toDomain()
    }
}
