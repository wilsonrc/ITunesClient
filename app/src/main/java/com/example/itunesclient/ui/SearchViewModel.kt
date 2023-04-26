package com.example.itunesclient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itunesclient.data.sources.remote.ISearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: ISearchRepository
) : ViewModel() {

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Empty)
    val searchUiState : StateFlow<SearchUiState> = _searchUiState

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