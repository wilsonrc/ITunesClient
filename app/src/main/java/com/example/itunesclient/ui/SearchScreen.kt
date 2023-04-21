package com.example.itunesclient.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.itunesclient.data.sources.remote.models.SearchResultsApiModel

@Composable
fun SearchScreen(onSearch: (String) -> Unit, uiState: SearchUiState) {
    Column {
        SearchBar {
            onSearch(it)
        }
        when (uiState) {
            is SearchUiState.Empty -> {
                //ADD THE TEXT TO THE CENTER OF THE SCREEN
                EmptyView()
            }
            is SearchUiState.Loading -> {
                LoadingIndicator()
            }
            is SearchUiState.Success -> {
                val list = uiState.albums
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(uiState.albums.size) {
                        AlbumCard(item = list[it])
                    }
                }
            }
            is SearchUiState.Error -> {
            }
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
fun AlbumCard(item: SearchResultsApiModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .zIndex(1f),
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
                    text = "Release Date: ${item.releaseDate}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    onSearch: (String) -> Unit
) {
    val (searchText, setSearchText) = remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Search Input
        OutlinedTextField(
            value = searchText,
            onValueChange = { text ->
                setSearchText(text)
            },
            modifier = Modifier
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

        // Search Icon Button
        IconButton(
            onClick = { onSearch(searchText) },
            modifier = Modifier
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