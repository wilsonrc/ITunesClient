package com.example.itunesclient.data.sources.remote.service

import com.example.itunesclient.data.sources.remote.ApiRoutes.SEARCH
import com.example.itunesclient.data.sources.remote.ApiRoutes.SEARCH_ENTITY_PARAM
import com.example.itunesclient.data.sources.remote.ApiRoutes.SEARCH_TERM_PARAM
import com.example.itunesclient.data.sources.remote.models.SearchApiModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*


class ApiService(
    private val client: HttpClient
) : IApiService {

    override suspend fun search(query: String): Result<SearchApiModel> {
        return try {
            val response = client.get {
                url(SEARCH)
                parameter(SEARCH_TERM_PARAM, query)
                parameter(SEARCH_ENTITY_PARAM, "album")
            }
           Result.success(response.body<SearchApiModel>())
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            Result.failure(Exception("Error: ${ex.response.status.description}"))
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            Result.failure(Exception("Error: ${ex.response.status.description}"))
        } catch (ex: ServerResponseException) {
            // 5xx - response
            println("Error: ${ex.response.status.description}")
            Result.failure(Exception("Error: ${ex.response.status.description}"))
        }
    }
}