package com.example.itunesclient.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.itunesclient.R
import com.example.itunesclient.data.SearchResult
import com.example.itunesclient.data.SearchResults
import com.example.itunesclient.data.sources.remote.models.SearchResultsApiModel
import com.example.itunesclient.ui.theme.ITunesClientTheme

@Composable
fun SearchScreen(searchViewModel: SearchViewModel = hiltViewModel(), modifier: Modifier) {
    val uiState by searchViewModel.searchUiState.collectAsState()
    SearchScreen(
        onSearch = { searchViewModel.search(it) },
        uiState = uiState,
        modifier = modifier
    )
}

@Composable
private fun SearchScreen(onSearch: (String) -> Unit, uiState: SearchUiState, modifier: Modifier) {
    Column {
        SearchBar(
            onSearch = {
                onSearch(it)
            },
            modifier = modifier
        )
        when (uiState) {
            is SearchUiState.Empty -> {
                EmptyView()
            }
            is SearchUiState.Loading -> {
                LoadingIndicator()
            }
            is SearchUiState.Success -> {
                AlbumList(uiState.albums)
            }
            is SearchUiState.Error -> {
                ErrorView(message = uiState.message)
            }
        }
    }
}

@Composable
private fun AlbumList(albums: List<SearchResult>) {
    LazyColumn {
        items(
            items = albums,
            key = {
                it.collectionId
            }
        ) {
            AlbumCard(item = it)
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun EmptyView() {
    Text(
        text = "Search for an album by Artist or Band",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
private fun ErrorView(message: String) {
    Text(
        text = message,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun AlbumCard(item: SearchResult) {
    val showDialog = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .zIndex(1f)
            .clickable { showDialog.value = true },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Artwork Image
            AsyncImage(
                model = item.artworkUrl100,
                contentDescription = "Album Artwork",
                modifier = Modifier.size(128.dp)
            )
            Column {
                Text(
                    text = item.collectionName,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Release Date: ${item.releaseDate.toDateTime()}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        if (showDialog.value) {
            Dialog(onDismissRequest = { showDialog.value = false }) {
                AlbumDetailsDialog(item = item) {
                    showDialog.value = false
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
    modifier: Modifier
) {
    val (searchText, setSearchText) = remember { mutableStateOf("") }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { text ->
                setSearchText(text)
            },
            modifier = modifier
                .weight(1f)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                ),
            label = { Text("Search") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch(searchText) }),
            shape = RoundedCornerShape(8.dp)
        )

        IconButton(
            onClick = { onSearch(searchText) },
            modifier = modifier
                .padding(start = 8.dp)
                .background(
                    shape = RoundedCornerShape(20),
                    color = MaterialTheme.colorScheme.primary
                )
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                tint = Color.White
            )
        }
    }
}

@Composable
fun AlbumDetailsDialog(
    item: SearchResult,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    stringResource(id = R.string.dialog_album_details_title),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(stringResource(R.string.dialog_album_details_album, item.collectionName))
                Text(
                    stringResource(
                        R.string.dialog_album_details_release_date,
                        item.releaseDate.toDateTime().toString()
                    )
                )
                Text(
                    stringResource(
                        R.string.dialog_album_details_primary_genre,
                        item.primaryGenreName
                    )
                )
                Text(
                    stringResource(
                        R.string.dialog_album_details_price,
                        item.collectionPrice.toString(), item.currency
                    )
                )
                Text(
                    stringResource(
                        R.string.dialog_album_details_copy_right,
                        item.copyright
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(R.string.dialog_dismiss_button))
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
                    SearchResult(
                        artistName = "Artist Name",
                        collectionName = "Collection Name",
                        artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/7b/7a/7b/7b7a7b7c-8b1f-8b1f-8b1f-8b1f8b1f8b1f/source/100x100bb.jpg",
                        releaseDate = "2021-01-01",
                        primaryGenreName = "Genre",
                        collectionPrice = 10.0,
                        currency = "USD",
                        artistId = 1,
                        copyright = "Copyright",
                        collectionType = "Album",
                        collectionId = 1
                    ),
                    SearchResult(
                        artistName = "Artist Name",
                        collectionName = "Collection Name",
                        artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/7b/7a/7b/7b7a7b7c-8b1f-8b1f-8b1f-8b1f8b1f8b1f/source/100x100bb.jpg",
                        releaseDate = "2021-01-01",
                        primaryGenreName = "Genre",
                        collectionPrice = 10.0,
                        currency = "USD",
                        artistId = 1,
                        copyright = "Copyright",
                        collectionType = "Album",
                        collectionId = 2
                    )
                )
            ),
            modifier = Modifier
        )
    }
}