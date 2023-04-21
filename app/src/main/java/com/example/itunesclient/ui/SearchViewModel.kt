package com.example.itunesclient.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itunesclient.data.sources.SearchRepository
import com.example.itunesclient.data.sources.remote.ISearchRepository
import com.example.itunesclient.data.sources.remote.SearchRemoteDataSource
import com.example.itunesclient.data.sources.remote.service.IApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(

) : ViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Empty)
    val searchUiState = _searchUiState
    private val searchRepository: ISearchRepository = SearchRepository(
        remoteDataSource = SearchRemoteDataSource(
            IApiService.create()
        ),
        dispatcher = Dispatchers.IO
    )

    fun search(query: String) = viewModelScope.launch {
        _searchUiState.value = SearchUiState.Loading
        val results = searchRepository.search(query)
        if (results.isSuccess) {
            results.getOrNull()?.let { searchResults ->
                _searchUiState.value = SearchUiState.Success(searchResults.results)
            }
        } else {
            val errorMessage = results.exceptionOrNull()?.message ?: "Error loading search results"
            _searchUiState.value = SearchUiState.Error(errorMessage)
        }
    }
}