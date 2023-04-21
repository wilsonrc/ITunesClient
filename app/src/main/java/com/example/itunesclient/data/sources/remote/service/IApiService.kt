package com.example.itunesclient.data.sources.remote.service

import com.example.itunesclient.data.sources.remote.models.SearchApiModel

interface IApiService {
    suspend fun search(query: String): Result<SearchApiModel>
}