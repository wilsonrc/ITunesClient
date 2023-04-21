package com.example.itunesclient.data.sources.remote.models

import com.example.itunesclient.data.SearchResult
import com.example.itunesclient.data.SearchResults
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchApiModel(
    @SerialName("resultCount")
    val resultCount: Int,
    @SerialName("results")
    val results: List<SearchResultsApiModel>
)
fun SearchApiModel.toSearchResults() : SearchResults {
    return SearchResults(
        resultCount = resultCount,
        results =  results.map {
            SearchResult(
                collectionType = it.collectionType,
                collectionId = it.collectionId,
                artistId = it.artistId,
                artistName = it.artistName,
                collectionName = it.collectionName,
                artworkUrl100 = it.artworkUrl100,
                collectionPrice = it.collectionPrice,
                copyright = it.copyright,
                currency = it.currency,
                releaseDate = it.releaseDate,
                primaryGenreName = it.primaryGenreName
            )
        }
    )
}

