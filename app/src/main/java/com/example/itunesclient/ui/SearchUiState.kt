package com.example.itunesclient.ui

import com.example.itunesclient.data.SearchResult

sealed class SearchUiState {
    object Empty : SearchUiState()
    object Loading : SearchUiState()
    data class Success(val albums: List<SearchResult>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}