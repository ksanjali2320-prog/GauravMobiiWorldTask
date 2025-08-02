package com.gks.gauravmobiiworldtask.data.remote.api

import com.gks.gauravmobiiworldtask.data.remote.dto.RepositoryDto
import retrofit2.http.GET

interface GithubApi {
    @GET("users/square/repos")
    suspend fun getRepositories(): List<RepositoryDto>
}
