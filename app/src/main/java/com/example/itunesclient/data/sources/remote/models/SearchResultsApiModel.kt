package com.example.itunesclient.data.sources.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultsApiModel(
    @SerialName("collectionType")
    val collectionType: String,
    @SerialName("artistId")
    val artistId: Int,
    @SerialName("artistName")
    val artistName: String,
    @SerialName("collectionName")
    val collectionName: String,
    @SerialName("artworkUrl100")
    val artworkUrl100: String,
    @SerialName("collectionPrice")
    val collectionPrice: Double? = null,
    @SerialName("copyright")
    val copyright: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("releaseDate")
    val releaseDate: String,
    @SerialName("primaryGenreName")
    val primaryGenreName: String
)
