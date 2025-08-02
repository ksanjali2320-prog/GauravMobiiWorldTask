package com.gks.gauravmobiiworldtask.di

import android.content.Context
import androidx.room.Room
import com.gks.gauravmobiiworldtask.BuildConfig
import com.gks.gauravmobiiworldtask.data.local.dao.RepositoryDao
import com.gks.gauravmobiiworldtask.data.local.database.AppDatabase
import com.gks.gauravmobiiworldtask.data.remote.api.GithubApi
import com.gks.gauravmobiiworldtask.data.repository.RepositoryRepositoryImpl
import com.gks.gauravmobiiworldtask.domain.repository.RepositoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): GithubApi = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GithubApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()

    @Provides
    fun provideDao(db: AppDatabase): RepositoryDao = db.repositoryDao()

    @Provides
    @Singleton
    fun provideRepository(api: GithubApi, dao: RepositoryDao): RepositoryRepository =
        RepositoryRepositoryImpl(api, dao)
}

