package com.gks.gauravmobiiworldtask.data.remote.api

import com.gks.gauravmobiiworldtask.data.remote.dto.RepositoryDto
import com.gks.gauravmobiiworldtask.util.Constant
import retrofit2.http.GET

interface GithubApi {
    @GET(Constant.API_KEY_ALL_USERS_FETCH)
    suspend fun getRepositories(): List<RepositoryDto>
}
