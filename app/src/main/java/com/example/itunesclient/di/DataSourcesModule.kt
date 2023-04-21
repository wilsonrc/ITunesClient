package com.example.itunesclient.di

import com.example.itunesclient.data.sources.ISearchDataSource
import com.example.itunesclient.data.sources.SearchRepository
import com.example.itunesclient.data.sources.remote.ISearchRepository
import com.example.itunesclient.data.sources.remote.SearchRemoteDataSource
import com.example.itunesclient.data.sources.remote.service.IApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
class DataSourcesModule {

    @Provides
    @ViewModelScoped
    fun provideSearchRemoteDataSource(apiService: IApiService): ISearchDataSource {
        return SearchRemoteDataSource(
            apiService = apiService
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSearchRepository(
        remoteDataSource: ISearchDataSource,
        @IODispatcher dispatcher: CoroutineDispatcher
    ): ISearchRepository {
        return SearchRepository(
            remoteDataSource = remoteDataSource,
            dispatcher = dispatcher
        )
    }

    @Provides
    @ViewModelScoped
    fun provideApiService(): IApiService {
        return IApiService.create()
    }

}