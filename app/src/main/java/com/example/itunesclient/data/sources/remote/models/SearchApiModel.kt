package com.example.itunesclient.data.sources.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class SearchApiModel(
    val resultCount: Int,
    val results: List<SearchResultsApiModel>
)

