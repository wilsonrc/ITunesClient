package com.example.itunesclient.data.sources.remote.service

import com.example.itunesclient.data.sources.remote.models.SearchApiModel
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

interface IApiService {
    suspend fun search(query: String): Result<SearchApiModel>

    companion object {
       fun create() : IApiService {
           return ApiService(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(ContentNegotiation){
                        json(
                            json = getJson()
                        )
                    }
                }
           )
       }

        fun getJson() = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }
}