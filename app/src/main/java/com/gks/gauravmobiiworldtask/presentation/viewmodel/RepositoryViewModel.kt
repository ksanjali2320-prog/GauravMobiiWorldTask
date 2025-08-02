package com.gks.gauravmobiiworldtask.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gks.gauravmobiiworldtask.domain.model.Repository
import com.gks.gauravmobiiworldtask.domain.model.UiState
import com.gks.gauravmobiiworldtask.domain.repository.RepositoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val repository: RepositoryRepository
) : ViewModel() {

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    var page by mutableIntStateOf(0)
        private set

    private val pageSize = 10

    init {
        fetchInitialData()
    }

    private fun fetchInitialData() {
        viewModelScope.launch {
            try {
                uiState = UiState.Loading
                repository.fetchAndCacheRepositories()
                loadMore()
            } catch (e: Exception) {
                uiState = UiState.Error("Failed to fetch data: ${e.localizedMessage}")
            }
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            try {
                val newRepos = repository.getPaginatedRepos(page, pageSize)
                val current = (uiState as? UiState.Success)?.repos ?: emptyList()
                val combined = current + newRepos
                uiState = UiState.Success(combined)
                page++
            } catch (e: Exception) {
                uiState = UiState.Error("Failed to load more: ${e.localizedMessage}")
            }
        }
    }

    var selectedRepo by mutableStateOf<Repository?>(null)
        private set

    fun loadRepositoryDetail(id: Int) {
        viewModelScope.launch {
            try {
                selectedRepo = repository.getRepositoryDetail(id)
            } catch (e: Exception) {
                uiState = UiState.Error("Failed to load detail: ${e.localizedMessage}")
            }
        }
    }
}

