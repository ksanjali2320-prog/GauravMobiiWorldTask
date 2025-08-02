package com.gks.gauravmobiiworldtask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gks.gauravmobiiworldtask.data.local.entity.RepositoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepositoryEntity>)

    @Query("SELECT * FROM repositories ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPaginatedRepos(limit: Int, offset: Int): List<RepositoryEntity>

    @Query("DELETE FROM repositories")
    suspend fun clearAll()

    @Query("SELECT * FROM repositories WHERE id = :userId")
    fun getRepositoryById(userId: Int): Flow<RepositoryEntity>

    @Query("UPDATE repositories SET addToBookMark = :isBookmarked WHERE id = :repoId")
    suspend fun updateBookMarkStatus(repoId: Int, isBookmarked: Boolean)
}
