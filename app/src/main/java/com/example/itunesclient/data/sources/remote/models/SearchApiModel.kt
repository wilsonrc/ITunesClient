package com.example.itunesclient.data.sources.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchApiModel(
    @SerialName("resultCount")
    val resultCount: Int,
    @SerialName("results")
    val results: List<SearchResultsApiModel>
)

