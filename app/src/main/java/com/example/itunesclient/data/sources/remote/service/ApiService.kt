package com.example.itunesclient.data.sources.remote.service

import com.example.itunesclient.data.sources.remote.ApiRoutes.SEARCH
import com.example.itunesclient.data.sources.remote.ApiRoutes.SEARCH_ENTITY_PARAM
import com.example.itunesclient.data.sources.remote.ApiRoutes.SEARCH_TERM_PARAM
import com.example.itunesclient.data.sources.remote.models.SearchApiModel
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json


class ApiService(
    private val client: HttpClient,
    private val json: Json
) : IApiService {

    override suspend fun search(query: String): Result<SearchApiModel> {
        return runCatching {
            val response = client.get {
                contentType(ContentType.Application.Json)
                url(SEARCH)
                parameter(SEARCH_TERM_PARAM, query)
                parameter(SEARCH_ENTITY_PARAM, "album")
            }

            json.decodeFromString(SearchApiModel.serializer(), response.bodyAsText())
        }
    }
}