package com.example.composedemo.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.algolia.search.helper.deserialize
import com.example.composedemo.model.AlgoliaProduct
import com.example.composedemo.ui.FilterBottomSheetContent
import com.example.composedemo.ui.FloatingFilterSortBar
import com.example.composedemo.ui.SortBottomSheetContent
import com.example.composedemo.ui.product.ProductGridCard
import com.example.composedemo.ui.product.ProductListCard
import com.example.composedemo.viewmodel.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultsScreen(query: String, viewModel: SearchViewModel = viewModel()) {
    var results by remember { mutableStateOf<List<AlgoliaProduct>>(emptyList()) }
    var isGrid by remember { mutableStateOf(true) }
    var resultCount by remember { mutableStateOf(0) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var sheetContent by remember { mutableStateOf<@Composable () -> Unit>({}) }
    var selectedSort by remember { mutableStateOf("Relevance") }

    LaunchedEffect(query) {
        viewModel.searcher.setQuery(query)
        val response = withContext(Dispatchers.IO) { viewModel.searcher.search() }
        response?.let {
            resultCount = it.nbHitsOrNull ?: 0
            results = it.hits.deserialize(AlgoliaProduct.serializer())
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { isGrid = true }) {
                    Icon(
                        Icons.Default.GridView,
                        contentDescription = "Grid View",
                        tint = if (isGrid) Color(0xFF00685E) else Color.Gray
                    )
                }
                IconButton(onClick = { isGrid = false }) {
                    Icon(
                        Icons.Default.ViewList,
                        contentDescription = "List View",
                        tint = if (!isGrid) Color(0xFF00685E) else Color.Gray
                    )
                }
            }

            Text(
                text = "SEARCH RESULTS ‘$query’ ($resultCount)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )

            if (isGrid) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(results.size) { index ->
                        ProductGridCard(product = results[index]) {}
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(results) { product ->
                        ProductListCard(product = product) {}
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            FloatingFilterSortBar(
                onFilterClick = {
                    sheetContent = { FilterBottomSheetContent() }
                    coroutineScope.launch { sheetState.show() }
                },
                onSortClick = {
                    sheetContent = {
                        SortBottomSheetContent(
                            selectedSort = selectedSort,
                            onSortSelected = {
                                selectedSort = it
                            },
                            onDismissRequest = {
                                coroutineScope.launch { sheetState.hide() }
                            }
                        )
                    }
                    coroutineScope.launch { sheetState.show() }
                }
            )
        }

        if (sheetState.isVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    coroutineScope.launch { sheetState.hide() }
                },
                sheetState = sheetState
            ) {
                sheetContent()
            }
        }
    }
}
