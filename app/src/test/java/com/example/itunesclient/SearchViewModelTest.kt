package com.example.itunesclient

import com.example.itunesclient.data.SearchResult
import com.example.itunesclient.data.SearchResults
import com.example.itunesclient.data.sources.remote.ISearchRepository
import com.example.itunesclient.data.sources.remote.models.SearchApiModel
import com.example.itunesclient.data.sources.remote.models.SearchResultsApiModel
import com.example.itunesclient.ui.SearchUiState
import com.example.itunesclient.ui.SearchViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @MockK
    private lateinit var searchRepository: ISearchRepository

    private lateinit var viewModel: SearchViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = SearchViewModel(searchRepository)
    }

    @Test
    fun `search with successful results`() = runTest {
        val query = "test"
        val searchResults = listOf(
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
                artistName = "Artist Name 2",
                collectionName = "Collection Name",
                artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/7b/7a/7b/7b7a7b7c-8b1f-8b1f-8b1f-8b1f8b1f8b1f/source/100x100bb.jpg",
                releaseDate = "2021-01-01",
                primaryGenreName = "Genre",
                collectionPrice = 10.0,
                currency = "USD",
                artistId = 2,
                copyright = "Copyright",
                collectionType = "Album",
                collectionId = 1
            )
        )

        coEvery { searchRepository.search(query) } returns Result.success(
            SearchResults(
                resultCount = 2,
                results = searchResults
            )
        )

        viewModel.search(query)

        val expectedState = SearchUiState.Success(searchResults)
        val actualState = viewModel.searchUiState.first()

        assertEquals(expectedState, actualState)
    }

    @Test
    fun `search with empty results`() = runTest {
        val query = "test"
        val searchResults = listOf<SearchResult>()

        coEvery { searchRepository.search(query) } returns Result.success(
            SearchResults(
                resultCount = 0,
                results = searchResults
            )
        )

        viewModel.search(query)

        val expectedState = SearchUiState.Success(searchResults)
        val actualState = viewModel.searchUiState.first()

        assertEquals(expectedState, actualState)
    }

    @Test
    fun `search with failure`() = runTest {
        val query = "test"
        val errorMessage = "Error loading search results"
        val exception = Exception(errorMessage)

        coEvery { searchRepository.search(query) } returns Result.failure(exception)

        viewModel.search(query)

        val expectedState = SearchUiState.Error(errorMessage)
        val actualState = viewModel.searchUiState.first()

        assertEquals(expectedState, actualState)
    }

    @Test
    fun `initial state is empty`() = runTest {
        val expectedState = SearchUiState.Empty
        val actualState = viewModel.searchUiState.first()

        assertEquals(expectedState, actualState)
    }
}