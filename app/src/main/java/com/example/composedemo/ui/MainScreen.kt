package com.example.composedemo.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.model.AlgoliaProduct
import com.example.composedemo.model.Banner
import com.example.composedemo.ui.theme.SearchBar

@Composable
fun MainScreen(
    products: List<AlgoliaProduct>,
    banners: List<Banner>,
    onProductClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
    ) {
       // item { SearchBar() }
        item { BannerCarousel(banners) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { NewArrivalsSection() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { ProductCarousel(products, onProductClick) }
    }
}