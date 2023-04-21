package com.example.itunesclient.ui

import com.example.itunesclient.data.sources.remote.models.SearchResultsApiModel

sealed class SearchUiState {
    object Empty : SearchUiState()
    object Loading : SearchUiState()
    data class Success(val albums: List<SearchResultsApiModel>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}