package com.example.itunesclient.di

import com.example.itunesclient.data.sources.ISearchDataSource
import com.example.itunesclient.data.sources.SearchRepository
import com.example.itunesclient.data.sources.remote.ISearchRepository
import com.example.itunesclient.data.sources.remote.SearchRemoteDataSource
import com.example.itunesclient.data.sources.remote.service.ApiService
import com.example.itunesclient.data.sources.remote.service.IApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json

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
    fun provideApiService(httpClient: HttpClient, json: Json): IApiService {
        return ApiService(httpClient, json)
    }

    @Provides
    @ViewModelScoped
    fun provideHttpClient(json: Json): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    json = json
                )
            }
        }
    }

    @Provides
    @ViewModelScoped
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }

}