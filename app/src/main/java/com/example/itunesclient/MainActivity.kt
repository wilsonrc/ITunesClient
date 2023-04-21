package com.example.itunesclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.itunesclient.data.sources.remote.models.SearchResultsApiModel
import com.example.itunesclient.ui.SearchScreen
import com.example.itunesclient.ui.SearchUiState
import com.example.itunesclient.ui.SearchViewModel
import com.example.itunesclient.ui.theme.ITunesClientTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ITunesClientTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by searchViewModel.searchUiState.collectAsState()
                    SearchScreen(
                        onSearch = { searchViewModel.search(it) },
                        uiState = uiState
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ITunesClientTheme {
        SearchScreen(
            onSearch = { },
            uiState = SearchUiState.Success(
                listOf(
                    SearchResultsApiModel(
                        artistName = "Artist Name",
                        collectionName = "Collection Name",
                        artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/7b/7a/7b/7b7a7b7c-8b1f-8b1f-8b1f-8b1f8b1f8b1f/source/100x100bb.jpg",
                        releaseDate = "2021-01-01",
                        primaryGenreName = "Genre",
                        collectionPrice = 10.0,
                        currency = "USD",
                        artistId = 1,
                        copyright = "Copyright",
                        collectionType = "Album"
                    ),
                    SearchResultsApiModel(
                        artistName = "Artist Name",
                        collectionName = "Collection Name",
                        artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/7b/7a/7b/7b7a7b7c-8b1f-8b1f-8b1f-8b1f8b1f8b1f/source/100x100bb.jpg",
                        releaseDate = "2021-01-01",
                        primaryGenreName = "Genre",
                        collectionPrice = 10.0,
                        currency = "USD",
                        artistId = 1,
                        copyright = "Copyright",
                        collectionType = "Album"
                    )
                )
            )
        )
    }
}