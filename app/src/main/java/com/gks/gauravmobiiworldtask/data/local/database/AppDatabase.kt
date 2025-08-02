package com.gks.gauravmobiiworldtask.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gks.gauravmobiiworldtask.data.local.dao.RepositoryDao
import com.gks.gauravmobiiworldtask.data.local.entity.RepositoryEntity

@Database(entities = [RepositoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}
