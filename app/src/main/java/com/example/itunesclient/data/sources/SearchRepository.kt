package com.example.itunesclient.data.sources

import com.example.itunesclient.data.sources.remote.ISearchRepository
import com.example.itunesclient.data.sources.remote.models.SearchApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SearchRepository(
    private val remoteDataSource: ISearchDataSource,
    private val dispatcher: CoroutineDispatcher
) : ISearchRepository {
    override suspend fun search(query: String): Result<SearchApiModel> {
        return withContext(dispatcher) {
            remoteDataSource.search(query)
        }
    }
}