package com.example.itunesclient.data.sources.remote

import com.example.itunesclient.data.sources.remote.models.SearchApiModel

interface ISearchRepository {
    suspend fun search(query: String): Result<SearchApiModel>
}