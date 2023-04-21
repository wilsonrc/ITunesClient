package com.example.itunesclient.data.sources.remote

import com.example.itunesclient.data.sources.ISearchDataSource
import com.example.itunesclient.data.sources.remote.models.SearchApiModel
import com.example.itunesclient.data.sources.remote.service.IApiService
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val apiService: IApiService
) : ISearchDataSource {
    override suspend fun search(query: String): Result<SearchApiModel> {
        return apiService.search(query)
    }
}