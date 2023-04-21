package com.example.itunesclient.data


data class SearchResults(
    var resultCount: Int,
    var results: List<SearchResult>
)


data class SearchResult(
    val collectionType: String,
    val collectionId: Int,
    val artistId: Int,
    val artistName: String,
    val collectionName: String,
    val artworkUrl100: String,
    val collectionPrice: Double? = null,
    val copyright: String,
    val currency: String,
    val releaseDate: String,
    val primaryGenreName: String
)