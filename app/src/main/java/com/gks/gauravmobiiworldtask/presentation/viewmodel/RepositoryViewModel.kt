package com.gks.gauravmobiiworldtask.presentation.viewmodel

import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val pageSize = 5

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


    private val _selectedRepo = MutableStateFlow<Repository?>(null)
    val selectedRepo: StateFlow<Repository?> = _selectedRepo.asStateFlow()

    fun loadRepositoryDetail(repoId: Int) {
        viewModelScope.launch {
            val repo = repository.getRepositoryDetail(repoId)
            _selectedRepo.value = repo
        }
    }

    fun onBookmarkToggleMain(repoId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            repository.updateBookMarkStatus(repoId, isBookmarked)

            val currentRepos = (uiState as? UiState.Success)?.repos?.toMutableList() ?: return@launch
            val index = currentRepos.indexOfFirst { it.id == repoId }
            if (index != -1) {
                val updatedRepo = currentRepos[index].copy(addToBookMark = isBookmarked)
                currentRepos[index] = updatedRepo
                uiState = UiState.Success(currentRepos)

                /**
                 *   Also update the main list
                 */
                if (_selectedRepo.value?.id == repoId) {
                    _selectedRepo.value = updatedRepo
                }
            }
        }
    }

    fun onBookmarkToggle(repoId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            repository.updateBookMarkStatus(repoId, isBookmarked)

            val updatedRepo = _selectedRepo.value?.copy(addToBookMark = isBookmarked)
            _selectedRepo.value = updatedRepo

            /**
             *   Also update the main list
             */
            val currentRepos = (uiState as? UiState.Success)?.repos?.toMutableList() ?: return@launch
            val index = currentRepos.indexOfFirst { it.id == repoId }
            if (index != -1 && updatedRepo != null) {
                currentRepos[index] = updatedRepo
                uiState = UiState.Success(currentRepos)
            }
        }
    }

    fun refreshVisibleRepos() {
//        Log.e("referesh clal","refresh call")
//        Log.e("referesh clal","refresh call")
//        Log.e("referesh clal","refresh call")
//        Log.e("referesh clal","refresh call")
//        Log.e("referesh clal","refresh call")
//        viewModelScope.launch {
//            try {
//                val refreshed = repository.getPaginatedRepos(0, (page + 1) * pageSize)
//                uiState = UiState.Success(refreshed)
//            } catch (e: Exception) {
//                uiState = UiState.Error("Failed to refresh: ${e.localizedMessage}")
//            }
//        }
    }
}

