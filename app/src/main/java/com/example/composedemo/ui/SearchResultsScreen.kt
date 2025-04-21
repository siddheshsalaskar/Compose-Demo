package com.example.composedemo.ui

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.algolia.search.helper.deserialize
import com.example.composedemo.model.AlgoliaProduct
import com.example.composedemo.ui.product.ProductCard
import com.example.composedemo.viewmodel.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun SearchResultsScreen(query: String, viewModel: SearchViewModel = viewModel()) {
    var results by remember { mutableStateOf<List<AlgoliaProduct>>(emptyList()) }
    var isGrid by remember { mutableStateOf(true) }
    var resultCount by remember { mutableStateOf(0) }

    LaunchedEffect(query) {
        viewModel.searcher.setQuery(query)
        val response = withContext(Dispatchers.IO) { viewModel.searcher.search() }
        response?.let {
            resultCount = it.nbHitsOrNull ?: 0
            results = it.hits.deserialize(AlgoliaProduct.serializer())
        }
    }

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
                    ProductCard(product = results[index]) {}
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(results) { product ->
                    ProductCard(product = product) {}
                }
            }
        }
    }
}
