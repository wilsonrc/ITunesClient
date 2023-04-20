package com.example.itunesclient.data.sources

import com.example.itunesclient.data.sources.remote.models.SearchApiModel

interface ISearchDataSource {
    suspend fun search(query: String): Result<SearchApiModel>
}